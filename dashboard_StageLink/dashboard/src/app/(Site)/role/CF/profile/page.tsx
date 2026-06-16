"use client"


import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";
import CFLayout from "@/components/Layouts/CFLayout";

import { useState, useEffect } from "react";
import Loader from "@/components/common/Loader";
import DisplayInput from "@/components/FormElements/displayInput";

type ChefFiliereDTO = {
    idChefFiliere: number;
    nomFiliere: string;
    domaineDetude: string;
    ecoleId: number;
    utilisateurId: number;
    nom: string;
    prenom: string;
};


const Profile = () => {
    const [chefFiliereData, setChefFiliereData] = useState<ChefFiliereDTO | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        // Fetch `idUtilisateur` from localStorage
        const idUtilisateur = localStorage.getItem("idUtilisateur");
        const token = localStorage.getItem("token"); // Assumes token is stored as "Authorization"

        if (!idUtilisateur || !token) {
            setError("Missing user ID or authorization token");
            setLoading(false);
            return;
        }

        const fetchData = async () => {
            try {
                const response = await fetch(`http://localhost:8080/role/cf/${idUtilisateur}`, {
                    method: "GET",
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });

                if (!response.ok) {
                    if (response.status === 401) {
                        throw new Error("Unauthorized. Please check your token.");
                    } else if (response.status === 404) {
                        throw new Error("Data not found.");
                    } else {
                        throw new Error("An error occurred while fetching data.");
                    }
                }

                const data: ChefFiliereDTO = await response.json();
                setChefFiliereData(data);
            } catch (err: any) {
                setError(err.message || "An unknown error occurred.");
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, []);

    if (loading) return (<Loader></Loader>);
    if (error) return (<CFLayout>
            <div className="mx-auto w-full max-w-[970px]">
                <Breadcrumb pageName="Profile" /><p>Error: {error}</p>
            </div>
        </CFLayout>);

    localStorage.setItem('id', "" + chefFiliereData?.idChefFiliere);
    localStorage.setItem('nom_user', "" +chefFiliereData?.nom );
    localStorage.setItem('prenom_user', "" +chefFiliereData?.prenom );
    return (
        <CFLayout>
            <Breadcrumb pageName="Profile" />
            <div className="mx-auto w-full max-w-[970px] grid grid-cols-1 sm:grid-cols-2 gap-6">

                    <DisplayInput label={"Nom"} value={chefFiliereData?.nom || "empty"}/>
                    <DisplayInput label={"Prenom"} value={chefFiliereData?.prenom || "empty"}/>
                    <DisplayInput label={"Nom de la filiere"} value={chefFiliereData?.nomFiliere || "empty"}/>
                    <DisplayInput label={"Domaine d'étude"} value={chefFiliereData?.domaineDetude || "empty"}/>

            </div>
        </CFLayout>
    );
};

export default Profile;
