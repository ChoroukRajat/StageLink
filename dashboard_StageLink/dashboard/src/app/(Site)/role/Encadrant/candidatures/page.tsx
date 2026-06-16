"use client";
import React, { useState, useEffect } from "react";
import EncadrantLayout from "@/components/Layouts/EncadrantLayout";
import { FaPen } from "react-icons/fa";
import Loader from "@/components/common/Loader";
import DisplayInput from "@/components/FormElements/displayInput";
import Link from "next/link";

// Define the type for CandidatureDTO
type CandidatureDTO = {
    idCandidature: number;
    offreSelectionneeId: number;
    etudiantId: number;
    valide: boolean;
    notification: boolean;
    confirmationEtudiant: boolean;
    evaluation: string;
    createDate: Date;
    nomEtudiant: string;
    prenomEtudiant: string;
    titreProjet: string;
};

// Define the structure for School
interface School {
    idEcole: number;
    nomEcole: string;
}

const CandidatureTable = () => {
    const [candidatures, setCandidatures] = useState<CandidatureDTO[]>([]);
    const [schools, setSchools] = useState<School[]>([]);
    const [expandedRows, setExpandedRows] = useState<Record<number, any>>({});
    const [loading, setLoading] = useState(false);

    // Filters
    const [selectedSchool, setSelectedSchool] = useState<number | null>(null);
    const [isValide, setIsValide] = useState<string | null>(null);
    const [orderByDate, setOrderByDate] = useState<string>("desc");

    // Popup state
    const [showPopup, setShowPopup] = useState(false);
    const [editingCandidature, setEditingCandidature] = useState<CandidatureDTO | null>(null);


    // Internally assigned idEncadrant
    const idEncadrant = localStorage.getItem("idUtilisateur");

    const fetchSchools = async () => {
        try {
            const response = await fetch("http://localhost:8080/ecoles");
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const data: School[] = await response.json();
            setSchools(data);
        } catch (error) {
            console.error("Error fetching schools:", error);
        }
    };
    const fetchDocuments = async (idCandidature: number) => {
        try {
            const response = await fetch(`http://localhost:8080/docs/${idCandidature}`);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error("Error fetching documents:", error);
            return null;
        }
    };

    const fetchCandidatures = async () => {
        try {
            setLoading(true);
            const url = new URL(`http://localhost:8080/role/encadrant/${idEncadrant}`);
            const params: Record<string, string> = { orderByDate };

            if (selectedSchool) params.idEcole = selectedSchool.toString();
            if (isValide) params.isValide = isValide;

            Object.entries(params).forEach(([key, value]) => url.searchParams.append(key, value));

            const response = await fetch(url.toString());
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const data: CandidatureDTO[] = await response.json();
            const parsedData = data.map((c) => ({
                ...c,
                createDate: new Date(c.createDate),
            }));

            setCandidatures(parsedData);
        } catch (error) {
            console.error("Error fetching candidatures:", error);
        } finally {
            setLoading(false);
        }
    };

    const handleUpdateClick = (candidature: CandidatureDTO) => {
        setEditingCandidature(candidature);
        setShowPopup(true);
    };

    const handleSave = async () => {
        if (!editingCandidature) return;

        try {
            const response = await fetch(
                `http://localhost:8080/role/encadrant/${idEncadrant}/${editingCandidature.idCandidature}`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(editingCandidature),
                }
            );

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const message = await response.text();
            console.log("Candidature updated:", message);

            // Refresh the table after saving
            fetchCandidatures();
            setShowPopup(false);
        } catch (error) {
            console.error("Error updating candidature:", error);
        }
    };

    const fetchEtudiantDetails = async (etudiantId: number) => {
        const token = localStorage.getItem("token");
        try {
            const response = await fetch(`http://localhost:8080/role/encadrant/${etudiantId}`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error("Error fetching Etudiant details:", error);
            return null;
        }
    };

    const fetchOffreDetails = async (offreId: number) => {
        try {
            const response = await fetch(`http://localhost:8080/role/encadrant/offres/${offreId}`);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error("Error fetching Offre details:", error);
            return null;
        }
    };

    const toggleRowExpansion = async (rowIndex: number, type: string, id: number) => {
        if (expandedRows[rowIndex]?.[type]) {
            setExpandedRows((prev) => ({
                ...prev,
                [rowIndex]: { ...prev[rowIndex], [type]: null },
            }));
            return;
        }

        let details: EtudiantDetails | OffreDetails | Document | null = null;

        if (type === "etudiant") {
            details = await fetchEtudiantDetails(id);
        } else if (type === "offre") {
            details = await fetchOffreDetails(id);
        }else if(type === "documents") {
            details = await fetchDocuments(editingCandidature?.idCandidature || id);
        }

        setExpandedRows((prev) => ({
            ...prev,
            [rowIndex]: { ...prev[rowIndex], [type]: details },
        }));
    };

    useEffect(() => {
        fetchSchools();
        fetchCandidatures();
    }, [selectedSchool, isValide, orderByDate]);

    if (loading) return (<Loader></Loader>);

    return (
        <EncadrantLayout>
            <div>
                <h1>Encadrant Candidatures</h1>

                {/* Filters */}
                <div className="filters mb-4">
                    <select
                        value={selectedSchool || ""}
                        onChange={(e) =>
                            setSelectedSchool(e.target.value ? parseInt(e.target.value) : null)
                        }
                        className="mr-4 border p-2"
                    >
                        <option value="">Select School</option>
                        {schools.map((school) => (
                            <option key={school.idEcole} value={school.idEcole}>
                                {school.nomEcole}
                            </option>
                        ))}
                    </select>

                    <select
                        value={isValide || ""}
                        onChange={(e) => setIsValide(e.target.value || null)}
                        className="mr-4 border p-2"
                    >
                        <option value="">All</option>
                        <option value="true">Valid</option>
                        <option value="false">Invalid</option>
                    </select>

                    <select
                        value={orderByDate}
                        onChange={(e) => setOrderByDate(e.target.value)}
                        className="border p-2"
                    >
                        <option value="desc">Newest First</option>
                        <option value="asc">Oldest First</option>
                    </select>
                </div>

                <table border={1} style={{ width: "100%", textAlign: "left" }} className="table-auto w-full border-collapse border border-gray-300">
                    <thead>
                    <tr className="bg-gray-100">
                        <th className="border px-4 py-2">Nom Prénom</th>
                        <th className="border px-4 py-2">Offre</th>
                        <th className="border px-4 py-2">Documents</th>
                        <th className="border px-4 py-2">Modifier</th>
                    </tr>
                    </thead>
                    <tbody>
                    {candidatures.map((candidature, index) => (
                        <React.Fragment key={candidature.idCandidature}>
                            <tr>
                                <td className="border px-4 py-2 bg-gray-50">
                                    <button
                                        onClick={() =>
                                            toggleRowExpansion(index, "etudiant", candidature.etudiantId)
                                        }
                                    >
                                        {candidature.nomEtudiant} {candidature.prenomEtudiant}
                                    </button>
                                </td>
                                <td>
                                    <button
                                        onClick={() =>
                                            toggleRowExpansion(index, "offre", candidature.offreSelectionneeId)
                                        }
                                    >
                                        {candidature.titreProjet}
                                    </button>
                                </td>
                                <td className="border px-4 py-2 bg-gray-50">
                                    <button
                                        onClick={() =>
                                            toggleRowExpansion(index, "documents", candidature.idCandidature)
                                        }
                                    >
                                        Documents
                                    </button>
                                </td>
                                <td className="justify-items-center">
                                    <div className="justify-center">
                                    <button onClick={() => handleUpdateClick(candidature)}>
                                        <FaPen className="text-blue-600" />
                                    </button>
                                    </div>
                                </td>
                            </tr>
                            {/* Expanded Row for Etudiant */}

                            {/* Expanded Row for Etudiant */}
                        {expandedRows[index]?.etudiant && (<tr>
                                <td colSpan={5} className="border px-4 py-2 bg-gray-50">
                                    <div>
                                        <p>
                                            <strong>Mention
                                                Etudiant:</strong> {expandedRows[index]?.etudiant.mentionEtudiant}
                                        </p>
                                        <p>
                                            <strong>Promotion
                                                Etudiant:</strong> {expandedRows[index]?.etudiant.anneePromotion}
                                        </p>
                                        <p>
                                            <strong>Telephone
                                                Etudiant:</strong> {expandedRows[index]?.etudiant.telephone}
                                        </p>
                                        <p>
                                            <strong>Adresse
                                                Etudiant:</strong> {expandedRows[index]?.etudiant.adresseEtudiant}
                                        </p>


                                    </div>
                                </td>
                            </tr>
                        )}
                            {/* Expanded Row for Offre */}
                            {expandedRows[index]?.offre && (
                                <tr>
                                    <td colSpan={5} className="border px-4 py-2 bg-gray-50">
                                        <div>
                                            <p>
                                                <strong>Titre du
                                                    projet:</strong> {expandedRows[index]?.offre.titreProjet}
                                            </p>
                                            <p>
                                                <strong>Objectifs:</strong> {expandedRows[index]?.offre.objectifsProjet}
                                            </p>
                                            <p>
                                                <strong>Date de création:</strong>{" "}
                                                {new Date(expandedRows[index]?.offre.createdDate).toLocaleDateString()}
                                            </p>
                                    </div>
                                </td>
                            </tr>
                        )}
                        {/* Expanded Row for Documents */}
                        {expandedRows[index]?.documents && (
                            <tr>
                                <td></td>
                                <td></td>
                                <td colSpan={3}>
                                    <strong>Documents:</strong>
                                    <ul>
                                        {expandedRows[index].documents.cvUrl && (
                                            <li>
                                                <Link
                                                    href={expandedRows[index].documents.cvUrl}
                                                    target="_blank"
                                                    rel="noopener noreferrer"
                                                >
                                                    CV Preview
                                                </Link>
                                            </li>
                                        )}
                                        {expandedRows[index].documents.motivationUrl && (
                                            <li>
                                                <Link
                                                    href={expandedRows[index].documents.motivationUrl}
                                                    target="_blank"
                                                    rel="noopener noreferrer"
                                                >
                                                    Lettre de Motivation Preview
                                                </Link>
                                            </li>
                                        )}

                                        {expandedRows[index].documents.rapportUrl && (
                                            <li>
                                                <Link
                                                    href={expandedRows[index].documents.rapportUrl}
                                                    target="_blank"
                                                    rel="noopener noreferrer"
                                                >
                                                    Rapport de Stage Preview
                                                </Link>
                                            </li>
                                        )}

                                    </ul>
                                </td>
                            </tr>
                        )}
                    </React.Fragment>
                ))}
                </tbody>
            </table>
        </div>
            {/* Update Popup */}
            {showPopup && editingCandidature && (
                <div className="popup fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-999999">
                    <div className="popup-content bg-white rounded-lg shadow-lg p-6 w-full max-w-md">
                        <h2 className="text-xl font-bold text-gray-800 mb-4">Update Candidature</h2>
                        <div className="space-y-4">
                            <DisplayInput label={"Nom et Prenom"}
                                          value={editingCandidature.nomEtudiant + editingCandidature.prenomEtudiant}></DisplayInput>
                            <DisplayInput label={"Titre du projet"}
                                          value={editingCandidature.titreProjet}></DisplayInput>
                            <label className="flex items-center space-x-2">
                                <span className="text-gray-700">Valide:</span>
                                <input
                                    type="checkbox"
                                    className="rounded border-gray-300 text-blue-500 focus:ring-blue-500"
                                    checked={editingCandidature.valide}
                                    onChange={(e) =>
                                        setEditingCandidature({...editingCandidature, valide: e.target.checked})
                                    }
                                />
                            </label>
                            <label className="mb-3 block text-body-sm font-medium text-dark dark:text-white">
                                <span className="text-gray-900">Evaluation:</span>

                                <input
                                    type="text"
                                    className="w-full rounded-[7px] border-[1.5px] border-stroke bg-transparent px-5.5 py-3 text-dark outline-none transition placeholder:text-dark-6 focus:border-primary active:border-primary disabled:cursor-default dark:border-dark-3 dark:bg-dark-2 dark:text-white dark:focus:border-primary"
                                    value={editingCandidature.evaluation || ""}
                                    onChange={(e) =>
                                        setEditingCandidature({...editingCandidature, evaluation: e.target.value})
                                    }
                                />
                            </label>


                        </div>
                        <div className="mt-6 flex justify-end space-x-3">
                            <button
                                onClick={handleSave}
                                className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400 focus:ring-offset-1"
                            >
                                Save
                            </button>
                            <button
                                onClick={() => setShowPopup(false)}
                                className="bg-gray-300 hover:bg-gray-400 text-gray-800 px-4 py-2 rounded-md focus:outline-none focus:ring-2 focus:ring-gray-200 focus:ring-offset-1"
                            >
                                Cancel
                            </button>
                        </div>
                    </div>
                </div>

            )}
        </EncadrantLayout>
    );
};

export default CandidatureTable;

// Define the structure of Etudiant details
interface EtudiantDetails {
    idEtudiant: number;
    nom: string;
    prenom: string;
    telephone: string;
    adresseEtudiant: string;
    anneePromotion: number;
    mentionEtudiant: string;
    assuranceEtudiant: boolean;
}

// Define the structure of Offre details
interface OffreDetails {
    idOffre: number;
    titreProjet: string;
    description: string;
    dateCreation: string;
}

interface Document {
    idCandidature: number;
    cvUrl: string;
    motivationUrl: string;
    rapportUrl: string;
}

