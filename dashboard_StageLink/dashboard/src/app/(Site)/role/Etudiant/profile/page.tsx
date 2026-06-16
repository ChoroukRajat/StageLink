"use client";

import React, { useEffect, useState } from "react";
import Loader from "@/components/common/Loader";
import EtudiantLayout from "@/components/Layouts/EtudiantLayout";
import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";
import DisplayInput from "@/components/FormElements/displayInput";

interface Etudiant {
    idEtudiant: number;
    nom: string;
    prenom: string;
    telephone: string;
    adresseEtudiant: string;
    anneePromotion: number;
    mentionEtudiant: string;
    assuranceEtudiant: boolean;
}


const EtudiantPage = () => {
    const [etudiant, setEtudiant] = useState<Etudiant | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    const etudiantId = localStorage.getItem("idUtilisateur"); // Replace with dynamic ID as needed

    useEffect(() => {
        const fetchEtudiantAndEntreprise = async () => {
            try {
                // Fetch the Etudiant information
                const etudiantResponse = await fetch(
                    `http://localhost:8080/role/etudiant/${etudiantId}`,
                    {
                        headers: {
                            Authorization: `Bearer ${localStorage.getItem("token")}`,
                        },
                    }
                );
                if (!etudiantResponse.ok) {
                    throw new Error("Failed to fetch Etudiant data");
                }
                const etudiantData: Etudiant = await etudiantResponse.json();
                setEtudiant(etudiantData);
            } catch (err: any) {
                setError(err.message || "An error occurred");
            } finally {
                setLoading(false);
            }
        };

        fetchEtudiantAndEntreprise();
    }, [etudiantId]);



    if (loading) {
        return <Loader />;
    }

    if (error) {
        return <EtudiantLayout><div>Error: {error}</div></EtudiantLayout>;
    }

    localStorage.setItem("id", `${etudiant?.idEtudiant}`);
    localStorage.setItem("nom_user", `${etudiant?.nom}`);
    localStorage.setItem("prenom_user", `${etudiant?.prenom}`);

    return (
        <EtudiantLayout>
            <Breadcrumb pageName="Profile" />
            <div className="container mx-auto p-4">
                <h1 className="text-2xl font-bold mb-4">Etudiant Details</h1>

                {/* Etudiant Information */}
                <div className="mx-auto w-full max-w-[970px] grid grid-cols-1 sm:grid-cols-2 gap-6">
                    <DisplayInput label={"Nom"} value={etudiant?.nom || "empty"} />
                    <DisplayInput label={"Prenom"} value={etudiant?.prenom || "empty"} />
                    <DisplayInput label={"Telephone"} value={etudiant?.telephone || "empty"} />
                    <DisplayInput label={"Adresse"} value={etudiant?.adresseEtudiant || "empty"} />
                    <DisplayInput
                        label={"Année de Promotion"}
                        value={etudiant?.anneePromotion?.toString() || "empty"}
                    />
                    <DisplayInput label={"Mention"} value={etudiant?.mentionEtudiant || "empty"} />
                    <DisplayInput
                        label={"Assurance"}
                        value={etudiant?.assuranceEtudiant ? "Oui" : "Non"}
                    />
                </div>

            </div>
        </EtudiantLayout>
    );
};
export default EtudiantPage;
