"use client";

import React, { useState, useEffect } from "react";
import { useEdgeStore } from "@/lib/edgestore";
import Loader from "@/components/common/Loader";
import EtudiantLayout from "@/components/Layouts/EtudiantLayout";

interface OffreDTO {
    idOffre: number;
    titreProjet: string;
    description: string;
    duree: string;
    competence: string;
    remuneration: number;
    encadrantId: number;
    typeId: number;
    createDate: string;
}

const OffresPage: React.FC = () => {
    const [offres, setOffres] = useState<OffreDTO[]>([]);
    const { edgestore } = useEdgeStore();
    const [ville, setVille] = useState<string | null>(null);
    const [sortOrder, setSortOrder] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const [showModal, setShowModal] = useState<boolean>(false);
    const [selectedOffre, setSelectedOffre] = useState<OffreDTO | null>(null);


    const [cvFile, setCvFile] = useState<File | null>(null);
    const [motivationFile, setMotivationFile] = useState<File | null>(null);

    const handleVilleChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        setVille(event.target.value);
    };

    const handleSortOrderChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        setSortOrder(event.target.value);
    };

    const handleRowClick = (offre: OffreDTO) => {
        setSelectedOffre(offre);
        setShowModal(true);
    };

    const handleFileChange = (
        e: React.ChangeEvent<HTMLInputElement>,
        fileType: "cv" | "motivation"
    ) => {
        const file = e.target.files?.[0] || null;
        if (fileType === "cv") setCvFile(file);
        if (fileType === "motivation") setMotivationFile(file);
    };

    const handleSubmit = async () => {
        if (!cvFile || !motivationFile || !selectedOffre) {
            alert("Please upload both files!");
            return;
        }

        const idUtilisateur = localStorage.getItem("idUtilisateur");
        const token = localStorage.getItem("token");

        if (!idUtilisateur || !token) {
            alert("Authentication required.");
            return;
        }
        try{

            let res = await edgestore.publicFiles.upload({file: cvFile});
            const cvUrl = res.url;
            



            res = await edgestore.publicFiles.upload({file: motivationFile});
            const motivUrl = res.url;


        const response = await fetch(
            `http://localhost:8080/role/etudiant/${idUtilisateur}/candidature?idOffre=${selectedOffre.idOffre}&cv=${cvUrl}&motivation=${motivUrl}`
            ,
            {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                }
            }
        );

        if (response.ok) {
            alert("Candidature submitted successfully!");
            setShowModal(false);
        } else if (response.status === 400) {
            alert("Bad request. Please verify your data.");
        } else {
            alert("Failed to submit candidature. Please try again.");
        }
    } catch (error) {
        console.error("Submission Error:", error);
        alert("An error occurred while submitting candidature.");
    }



    }

    useEffect(() => {
        const fetchOffres = async () => {
            setLoading(true);
            setError(null);
            try {
                const idUtilisateur = localStorage.getItem("idUtilisateur");
                const token = localStorage.getItem("token");

                if (!idUtilisateur || !token) {
                    setError("User not authenticated.");
                    setLoading(false);
                    return;
                }

                const url = new URL(
                    `http://localhost:8080/role/etudiant/${idUtilisateur}/offres`
                );
                if (ville) url.searchParams.append("idVille", ville);
                if (sortOrder) url.searchParams.append("dateSort", sortOrder);

                const response = await fetch(url.toString(), {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });

                if (!response.ok) {
                    if (response.status === 404) {
                        throw new Error("No content available.");
                    } else {
                        throw new Error("Failed to fetch offres.");
                    }
                }

                const data: OffreDTO[] = await response.json();
                setOffres(data);
            } catch (err: any) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchOffres();
    }, [ville, sortOrder]);

    if (loading) return <Loader />;

    if (error) {
        return (
            <EtudiantLayout>
                <div className="text-red-500 text-center py-4">{error}</div>
            </EtudiantLayout>
        );
    }

    return (
        <EtudiantLayout>
            <div className="container mx-auto p-4">
                <h1 className="text-2xl font-bold mb-4">Offres Disponibles</h1>

                {/* Filters */}
                <div className="flex gap-4 mb-6">
                    <div>
                        <label htmlFor="ville" className="block font-medium mb-1">
                            Filtrer par Ville :
                        </label>
                        <select
                            id="ville"
                            className="border rounded px-2 py-1"
                            onChange={handleVilleChange}
                        >
                            <option value="">Toutes les villes</option>
                            <option value="1">Casablanca</option>
                            <option value="2">Rabat</option>
                        </select>
                    </div>

                    <div>
                        <label htmlFor="sortOrder" className="block font-medium mb-1">
                            Trier par :
                        </label>
                        <select
                            id="sortOrder"
                            className="border rounded px-2 py-1"
                            onChange={handleSortOrderChange}
                        >
                            <option value="">Par défaut</option>
                            <option value="ascendant">Plus anciens</option>
                            <option value="descendant">Plus récents</option>
                        </select>
                    </div>
                </div>

                {/* Table */}
                <table className="table-auto w-full border-collapse border border-gray-300">
                    <thead>
                    <tr className="bg-gray-200">
                        <th className="border border-gray-300 px-4 py-2">Titre du Projet</th>
                        <th className="border border-gray-300 px-4 py-2">Description</th>
                        <th className="border border-gray-300 px-4 py-2">Durée</th>
                        <th className="border border-gray-300 px-4 py-2">Compétences</th>
                        <th className="border border-gray-300 px-4 py-2">Rémunération</th>
                        <th className="border border-gray-300 px-4 py-2">Date de Création</th>
                    </tr>
                    </thead>
                    <tbody>
                    {offres.map((offre) => (
                        <tr
                            key={offre.idOffre}
                            onClick={() => handleRowClick(offre)}
                            className="cursor-pointer hover:bg-gray-100"
                        >
                            <td className="border border-gray-300 px-4 py-2">{offre.titreProjet}</td>
                            <td className="border border-gray-300 px-4 py-2">{offre.description}</td>
                            <td className="border border-gray-300 px-4 py-2">{offre.duree}</td>
                            <td className="border border-gray-300 px-4 py-2">{offre.competence}</td>
                            <td className="border border-gray-300 px-4 py-2">{offre.remuneration} DH</td>
                            <td className="border border-gray-300 px-4 py-2">
                                {new Date(offre.createDate).toLocaleDateString("fr-FR")}
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>

            {showModal && selectedOffre && (
                <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-999999">
                    <div className="bg-white p-6 rounded-lg w-1/3">
                        <h2 className="text-lg font-bold mb-4">
                            Postuler à l offre : {selectedOffre.titreProjet}
                        </h2>
                        <div>
                            <label className="block mb-2 font-medium">CV:</label>
                            <input
                                type="file"
                                accept=".pdf,.doc,.docx"
                                onChange={(e) => handleFileChange(e, "cv")}
                                className="mb-4"
                            />
                        </div>
                        <div>
                            <label className="block mb-2 font-medium">Lettre de Motivation:</label>
                            <input
                                type="file"
                                accept=".pdf,.doc,.docx"
                                onChange={(e) => handleFileChange(e, "motivation")}
                                className="mb-4"
                            />
                        </div>
                        <div className="flex gap-4">
                            <button
                                onClick={handleSubmit}
                                className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
                            >
                                Envoyer
                            </button>
                            <button
                                onClick={() => setShowModal(false)}
                                className="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400"
                            >
                                Annuler
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </EtudiantLayout>
    );
};

export default OffresPage;
