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

const OffresPage = () => {
    const [offres, setOffres] = useState<Offre[]>([]);
    const [expandedRow, setExpandedRow] = useState<number | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    // State for dropdowns
    const [selectedVille, setSelectedVille] = useState<number>(1); // Default is Casablanca (id = 1)
    const [isAscending, setIsAscending] = useState<boolean>(true); // Default is ascending (recent first)

    // State for selected offers
    const [selectedOffers, setSelectedOffers] = useState<number[]>([]);
    const [isConfirmMode, setIsConfirmMode] = useState<boolean>(false);

    useEffect(() => {
        const fetchOffres = async () => {
            try {
                const idUtilisateur = localStorage.getItem("idUtilisateur");
                if (!idUtilisateur) {
                    setError("User ID not found in localStorage");
                    setLoading(false);
                    return;
                }

                // Fetch the data based on selected parameters
                const response = await fetch(
                    `http://localhost:8080/role/cf/offres?idVille=${selectedVille}&isAscending=${isAscending}`
                );

                if (!response.ok) {
                    throw new Error(`Failed to fetch: ${response.status}`);
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
    }, [selectedVille, isAscending]); // Re-run when the dropdown values change

    const toggleRow = (id: number) => {
        setExpandedRow((prev) => (prev === id ? null : id));
    };

    const handleSelectClick = () => {
        setIsConfirmMode(true);
    };

    const handleConfirmClick = async () => {
        const idcfString = localStorage.getItem("id");
        const idcf = idcfString ? Number(idcfString) : null;
        if (!idcf) {
            setError("User ID not found in localStorage");
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/role/cf/${idcf}/offres/os`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(selectedOffers),
            });

            if (!response.ok) {
                throw new Error(`Failed to save offers: ${response.status}`);
            }

            alert("Selected offers have been successfully saved.");
            setSelectedOffers([]);
            setIsConfirmMode(false);
        } catch (err: any) {
            setError(err.message || "An error occurred while saving offers.");
        }
    };

    const handleCheckboxChange = (id: number) => {
        setSelectedOffers((prev) =>
            prev.includes(id) ? prev.filter((offerId) => offerId !== id) : [...prev, id]
        );
    };

    if (loading) {
        return (<Loader></Loader>);
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <CFLayout>
            <div className="container mx-auto p-4">
                <h1 className="text-2xl font-bold mb-4">List of Offres</h1>

                {/* Dropdown for Ville */}
                <div className="mb-4">
                    <label htmlFor="ville" className="mr-2">Select Ville: </label>
                    <select
                        id="ville"
                        value={selectedVille}
                        onChange={(e) => setSelectedVille(Number(e.target.value))}
                        className="border border-gray-300 rounded p-2"
                    >
                        <option value={1}>Casablanca</option>
                        <option value={2}>Rabat</option>
                    </select>
                </div>

                {/* Dropdown for Sorting */}
                <div className="mb-4">
                    <label htmlFor="sortOrder" className="mr-2">Sort by: </label>
                    <select
                        id="sortOrder"
                        value={isAscending ? "asc" : "desc"}
                        onChange={(e) => setIsAscending(e.target.value === "asc")}
                        className="border border-gray-300 rounded p-2"
                    >
                        <option value="asc">Recent First</option>
                        <option value="desc">Older First</option>
                    </select>
                </div>

                {offres.length === 0 ? (
                    <p>No offers available.</p>
                ) : (
                    <table className="table-auto w-full border-collapse border border-gray-300">
                        <thead>
                        <tr className="bg-gray-100">
                            <th className="border px-4 py-2">Titre Projet</th>
                            <th className="border px-4 py-2">Durée</th>
                            <th className="border px-4 py-2">Rémunération</th>
                            <th className="border px-4 py-2">Select</th>
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
                                        {isConfirmMode && (
                                            <input
                                                type="checkbox"
                                                checked={selectedOffers.includes(offre.idOffre)}
                                                onChange={() => handleCheckboxChange(offre.idOffre)}
                                            />
                                        )}
                                    </td>
                                    <td className="border px-4 py-2 text-center">
                                        {expandedRow === offre.idOffre ? "Hide Details" : "Show Details"}
                                    </td>
                                </tr>

                                {/* Expanded row for details */}
                                {expandedRow === offre.idOffre && (
                                    <tr>
                                        <td colSpan={5} className="border px-4 py-2 bg-gray-50">
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

                {/* Select/Confirm Button */}
                <div className="mt-4">
                    {!isConfirmMode ? (
                        <button
                            onClick={handleSelectClick}
                            className="bg-blue-500 text-white px-4 py-2 rounded"
                        >
                            Select
                        </button>
                    ) : (
                        <button
                            onClick={handleConfirmClick}
                            className="bg-green-500 text-white px-4 py-2 rounded"
                        >
                            Confirm
                        </button>
                    )}
                </div>
            </div>
        </CFLayout>
    );
};

export default OffresPage;
