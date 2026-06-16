"use client";
import React, { useState, useEffect } from "react";
import EncadrantLayout from "@/components/Layouts/EncadrantLayout";

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

        let details: EtudiantDetails | OffreDetails | null = null;

        if (type === "etudiant") {
            details = await fetchEtudiantDetails(id);
        } else if (type === "offre") {
            details = await fetchOffreDetails(id);
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

    if (loading) return <p>Loading...</p>;

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
                                            setExpandedRows((prev) => ({
                                                ...prev,
                                                [index]: {
                                                    ...prev[index],
                                                    documents: !prev[index]?.documents,
                                                },
                                            }))
                                        }
                                    >
                                        Documents
                                    </button>
                                </td>
                            </tr>
                            {/* Expanded Row for Etudiant */}

                            {/* Expanded Row for Etudiant */}
                            {expandedRows[index]?.etudiant && (
                                <tr>
                                    <td colSpan={3}>
                                        <strong>Etudiant Details:</strong>
                                        <pre>{JSON.stringify(expandedRows[index].etudiant, null, 2)}</pre>
                                    </td>
                                </tr>
                            )}
                            {/* Expanded Row for Offre */}
                            {expandedRows[index]?.offre && (<>

                                    <tr>
                                        <td></td>
                                        <td colSpan={3}>
                                            <strong>Offre Details:</strong>
                                            <pre>{JSON.stringify(expandedRows[index].offre, null, 2)}</pre>
                                        </td>
                                        <td></td>
                                    </tr>

                                </>
                            )}
                            {/* Expanded Row for Documents */}
                            {expandedRows[index]?.documents && (
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td colSpan={3}>
                                        <strong>Documents:</strong>
                                        <ul>
                                            {/* Replace with actual document links */}
                                            <li>No documents available</li>
                                        </ul>
                                    </td>
                                </tr>
                            )}
                        </React.Fragment>
                    ))}
                    </tbody>
                </table>
            </div>
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

