"use client";

import React, { useEffect, useState } from "react";
import Loader from "@/components/common/Loader";
import EncadrantLayout from "@/components/Layouts/EncadrantLayout";
import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";
import DisplayInput from "@/components/FormElements/displayInput";

interface Encadrant {
    idEncadrant: number;
    profession: string;
    departement: string;
    entrepriseId: number;
    utilisateurId: number;
    nom: string;
    prenom: string;
}

interface Entreprise {
    idEntreprise: number;
    nomEntreprise: string;
    formeJuridique: string;
    adresseEntreprise: string;
    telephoneEntreprise: string;
    faxEntreprise: string;
    emailEntreprise: string;
}

const EncadrantEntreprisePage = () => {
    const [encadrant, setEncadrant] = useState<Encadrant | null>(null);
    const [entreprise, setEntreprise] = useState<Entreprise | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const [toggleEntreprise, setToggleEntreprise] = useState<boolean>(false);

    const encadrantId = localStorage.getItem("idUtilisateur"); // Example ID, replace it dynamically as needed

    useEffect(() => {
        const fetchEncadrantAndEntreprise = async () => {
            try {
                // Fetch the encadrant information
                const encadrantResponse = await fetch(
                    `http://localhost:8080/role/encadrant/${encadrantId}/infos`,
                    {
                        headers: {
                            Authorization: `Bearer ${localStorage.getItem("token")}`,
                        },
                    }
                );
                if (!encadrantResponse.ok) {
                    throw new Error("Failed to fetch Encadrant data");
                }
                const encadrantData: Encadrant = await encadrantResponse.json();
                setEncadrant(encadrantData);

                // Fetch the associated entreprise data using the entrepriseId from Encadrant
                const entrepriseResponse = await fetch(
                    `http://localhost:8080/entreprise/${encadrantData.entrepriseId}`
                );
                if (!entrepriseResponse.ok) {
                    throw new Error("Failed to fetch Entreprise data");
                }
                const entrepriseData: Entreprise = await entrepriseResponse.json();
                setEntreprise(entrepriseData);
            } catch (err: any) {
                setError(err.message || "An error occurred");
            } finally {
                setLoading(false);
            }
        };

        fetchEncadrantAndEntreprise();
    }, [encadrantId]);

    const toggleEntrepriseDetails = () => {
        setToggleEntreprise((prev) => !prev);
    };

    if (loading) {
        return(<Loader></Loader>);
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    localStorage.setItem('id', "" + encadrant?.idEncadrant);
    localStorage.setItem('nom_user', "" +encadrant?.nom );
    localStorage.setItem('prenom_user', "" +encadrant?.prenom );

    return (
        <EncadrantLayout>
            <Breadcrumb pageName="Profile" />
        <div className="container mx-auto p-4">
            <h1 className="text-2xl font-bold mb-4">Encadrant Details</h1>

            {/* Encadrant Information */}
            <div className="mx-auto w-full max-w-[970px] grid grid-cols-1 sm:grid-cols-2 gap-6">
                <DisplayInput label={"Nom"} value={encadrant?.nom || "empty"}/>
                <DisplayInput label={"Prenom"} value={encadrant?.prenom || "empty"}/>
                <DisplayInput label={"Profession"} value={encadrant?.profession || "empty"}/>
                <DisplayInput label={"Departement"} value={encadrant?.departement || "empty"}/>
            </div>

            {/* Entreprise Information */}
            <br/><br/>
            <div>
                <h2
                    className="text-2xl font-bold mb-4 text-blue-600 cursor-pointer"
                    onClick={toggleEntrepriseDetails}
                >
                    Nom Entreprise : {entreprise?.nomEntreprise}
                </h2>
                <br/>

                {toggleEntreprise && entreprise && (
                    <div className="mx-auto w-full max-w-[970px] grid grid-cols-1 sm:grid-cols-2 gap-6">
                        <DisplayInput label={"Forme Juridique"} value={entreprise.formeJuridique || "empty"}/>
                        <DisplayInput label={"Adresse"} value={entreprise.adresseEntreprise || "empty"}/>
                        <DisplayInput label={"Telephone"} value={entreprise.telephoneEntreprise || "empty"}/>
                        <DisplayInput label={"Fax"} value={entreprise.faxEntreprise || "empty"}/>
                        <DisplayInput label={"Email"} value={entreprise.emailEntreprise || "empty"}/>

                    </div>
                )}
            </div>
        </div></EncadrantLayout>
    );
};

export default EncadrantEntreprisePage;
