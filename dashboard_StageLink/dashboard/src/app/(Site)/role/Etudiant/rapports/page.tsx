'use client';

import React, { useEffect, useState } from 'react';
import EtudiantLayout from "@/components/Layouts/EtudiantLayout";
import Loader from "@/components/common/Loader";

type Rapport = {
    idEtudiant: number;
    nomEtudiant: string;
    prenomEtudiant: string;
    idCandidature: number;
    titreOffre: string;
    rapportLien: string;
};

const RapportPage = () => {
    const [rapports, setRapports] = useState<Rapport[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    const id = localStorage.getItem("idUtilisateur");

    const fetchRapports = async () => {
        try {
            const response = await fetch(`http://localhost:8080/role/etudiant/${id}/rapports`);
            if (!response.ok) {
                throw new Error(`Failed to fetch rapports: ${response.status}`);
            }
            const data = await response.json();
            setRapports(data);
        } catch (err: any) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchRapports();
    }, [id]);

    if (loading) {
        return (

                <Loader></Loader>

        );
    }

    if (error) {
        return (
            <EtudiantLayout>
            <div className="flex justify-center items-center min-h-screen">
                <div className="text-red-500 font-semibold">{error}</div>
            </div>
            </EtudiantLayout>
        );
    }

    return (
        <EtudiantLayout>
        <div className="min-h-screen bg-gray-100 py-10 px-4">
            <h1 className="text-3xl font-bold text-center mb-8 text-blue-600">
                Rapports Disponibles
            </h1>
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
                {rapports.map((rapport) => (
                    <div
                        key={rapport.idCandidature}
                        className="bg-white shadow-lg rounded-lg p-6 hover:shadow-xl transition-shadow duration-200"
                    >
                        <h2 className="text-xl font-semibold text-gray-800">
                            {rapport.nomEtudiant} {rapport.prenomEtudiant}
                        </h2>
                        <p className="text-gray-600 mb-4">
                            <strong>Offre:</strong> {rapport.titreOffre}
                        </p>
                        <a
                            href={rapport.rapportLien}
                            target="_blank"
                            rel="noopener noreferrer"
                            className="text-blue-500 underline hover:text-blue-700"
                        >
                            Voir le rapport
                        </a>
                    </div>
                ))}
            </div>
        </div>
        </EtudiantLayout>
    );
};

export default RapportPage;
