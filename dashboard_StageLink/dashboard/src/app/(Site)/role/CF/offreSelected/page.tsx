"use client";

import React, { useEffect, useState } from "react";
import CFLayout from "@/components/Layouts/CFLayout";
import Loader from "@/components/common/Loader";

interface Offre {
    idOffre: number;
    titreProjet: string;
    description: string;
    duree: string;
    objectifsProjet: string;
    competence: string;
    remuneration: boolean;
    encadrant: { idEncadrant: number; name: string }; // Modify based on actual Encadrant entity
    type: { idType: number; name: string }; // Modify based on actual Type entity
    ville: { idVille: number; name: string }; // Modify based on actual Ville entity
    createdDate: string;
}

const OffresSPage = () => {
    const [offres, setOffres] = useState<Offre[]>([]);
    const [expandedRow, setExpandedRow] = useState<number | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchOffres = async () => {
            try {
                const idChefFiliere = localStorage.getItem("id"); // Assuming idChefFiliere is stored in localStorage
                if (!idChefFiliere) {
                    setError("Chef Filiere ID not found in localStorage");
                    setLoading(false);
                    return;
                }

                // Call the updated API endpoint
                const response = await fetch(
                    `http://localhost:8080/role/cf/offreSelected?idChefFiliere=${idChefFiliere}`
                );

                if (!response.ok) {
                    throw new Error(`Failed to fetch: ${response.status}`);
                }
                if (response.status === 204) {
                    // Handle No Content response
                    setError("No content available");
                    setLoading(false);
                    return;
                }

                const data: Offre[] = await response.json();
                setOffres(data);
            } catch (err: any) {
                setError(err.message || "An error occurred");
            } finally {
                setLoading(false);
            }
        };

        fetchOffres();
    }, []); // Dependency array left empty to fetch only once

    const toggleRow = (id: number) => {
        setExpandedRow((prev) => (prev === id ? null : id));
    };

    if (loading) {
        return (<Loader></Loader>);
    }

    if (error) {
        return (
            <div>
                {error === "No content available" ? (
                    <CFLayout>
                        <h1 className="text-2xl font-bold mb-4">No Content to display</h1>
                    </CFLayout>
                ) : (
                    <div>Error: {error}</div>
                )}
            </div>
        );
    }



    return (
        <CFLayout>
            <div className="container mx-auto p-4">
                <h1 className="text-2xl font-bold mb-4">List of Offres</h1>

                {offres.length === 0 ? (
                    <p>No offers available.</p>
                ) : (
                    <table className="table-auto w-full border-collapse border border-gray-300">
                        <thead>
                        <tr className="bg-gray-100">
                            <th className="border px-4 py-2">Titre Projet</th>
                            <th className="border px-4 py-2">Durée</th>
                            <th className="border px-4 py-2">Rémunération</th>
                            <th className="border px-4 py-2">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {offres.map((offre) => (
                            <React.Fragment key={offre.idOffre}>
                                {/* Row for summary */}
                                <tr
                                    className="hover:bg-gray-50 cursor-pointer"
                                    onClick={() => toggleRow(offre.idOffre)}
                                >
                                    <td className="border px-4 py-2">{offre.titreProjet}</td>
                                    <td className="border px-4 py-2">{offre.duree}</td>
                                    <td className="border px-4 py-2">
                                        {offre.remuneration ? "Yes" : "No"}
                                    </td>
                                    <td className="border px-4 py-2 text-center">
                                        {expandedRow === offre.idOffre ? "Hide Details" : "Show Details"}
                                    </td>
                                </tr>

                                {/* Expanded row for details */}
                                {expandedRow === offre.idOffre && (
                                    <tr>
                                        <td colSpan={4} className="border px-4 py-2 bg-gray-50">
                                            <div>
                                                <p>
                                                    <strong>Description:</strong> {offre.description}
                                                </p>
                                                <p>
                                                    <strong>Objectifs:</strong> {offre.objectifsProjet}
                                                </p>
                                                <p>
                                                    <strong>Compétence:</strong> {offre.competence}
                                                </p>
                                                <p>
                                                    <strong>Ville:</strong> {offre.ville?.name || "Unknown"}
                                                </p>
                                                <p>
                                                    <strong>Date de création:</strong>{" "}
                                                    {new Date(offre.createdDate).toLocaleDateString()}
                                                </p>
                                            </div>
                                        </td>
                                    </tr>
                                )}
                            </React.Fragment>
                        ))}
                        </tbody>
                    </table>
                )}
            </div>
        </CFLayout>
    );
};

export default OffresSPage;
