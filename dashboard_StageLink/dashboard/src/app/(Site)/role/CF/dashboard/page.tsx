"use client"
import { useState, useEffect } from "react";
import { Pie, Bar } from "react-chartjs-2";
import {
    Chart as ChartJS,
    ArcElement,
    Tooltip,
    Legend,
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
} from "chart.js";
import CFLayout from "@/components/Layouts/CFLayout";

// Register chart components
ChartJS.register(ArcElement, Tooltip, Legend, CategoryScale, LinearScale, BarElement, Title);

type ValidatedApplications = {
    validated: number;
    notValidated: number;
};

type ApplicationsByOffre = {
    titreProjet: string;
    totalApplications: number;
};

type ApplicationsByEtudiant = {
    etudiantNom: string;
    etudiantPrenom: string;
    totalApplications: number;
};

type ApplicationsByType = {
    typeName: string;
    totalApplications: number;
};

export default function Dashboard() {
    const [validatedApplications, setValidatedApplications] = useState<ValidatedApplications | null>(null);
    const [applicationsByOffre, setApplicationsByOffre] = useState<ApplicationsByOffre[]>([]);
    const [applicationsByType, setApplicationsByType] = useState<ApplicationsByType[]>([]);
    const [applicationsByEtudiant, setApplicationsByEtudiant] = useState<ApplicationsByEtudiant[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const chefFiliereId = localStorage.getItem("id");

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            setError(null);

            try {
                const baseUrl = `http://localhost:8080/dashboard/cf/${chefFiliereId}`;

                const [validatedRes, byOffreRes, byTypeRes, byEtudiantRes] = await Promise.all([
                    fetch(`${baseUrl}/validatedApplications`),
                    fetch(`${baseUrl}/applicationsByOffre`),
                    fetch(`${baseUrl}/applicationsByType`),
                    fetch(`${baseUrl}/applicationsByEtudiant`),
                ]);

                if (!validatedRes.ok || !byOffreRes.ok || !byTypeRes.ok || !byEtudiantRes.ok) {
                    throw new Error("Failed to fetch some data.");
                }

                const validatedData = await validatedRes.json();
                const byOffreData = await byOffreRes.json();
                const byTypeData = await byTypeRes.json();
                const byEtudiantData = await byEtudiantRes.json();

                setValidatedApplications(validatedData);
                setApplicationsByOffre(byOffreData);
                setApplicationsByType(byTypeData);
                setApplicationsByEtudiant(byEtudiantData);

            } catch (err) {
                setError("An error occurred while fetching data.");
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, [chefFiliereId]);

    if (loading) return <div className="text-center text-lg">Loading...</div>;
    if (error) return <div className="text-center text-red-500">{error}</div>;

    return (
        <CFLayout>
            <h1 className="text-2xl font-bold mb-6">Dashboard - Chef Filière</h1>
            <div className="flex flex-wrap justify-between container mx-auto p-6">


                {/* Validated Applications - Pie Chart */}
                {validatedApplications && (
                    <div className="mb-8 w-2/5 bg-white shadow-md rounded p-4 ">
                        <h2 className="text-xl font-semibold mb-4">Validated vs Non-Validated Applications</h2>
                        <Pie
                            data={{
                                labels: ["Validated", "Not Validated"],
                                datasets: [
                                    {
                                        label: "Applications",
                                        data: [
                                            validatedApplications.validated,
                                            validatedApplications.notValidated,
                                        ],
                                        backgroundColor: ["#FF6384", "#FFCE56"],
                                    },
                                ],
                            }}
                            options={{responsive: true, plugins: {legend: {position: "top"}}}}
                        />
                    </div>
                )}

                {/* Applications by Offre - Bar Chart */}
                <div className="mb-8 w-1/2 bg-white shadow-md rounded p-4">
                    <h2 className="text-xl font-semibold mb-4">Applications by Offre</h2>
                    <Bar
                        data={{
                            labels: applicationsByOffre.map((offre) => offre.titreProjet),
                            datasets: [
                                {
                                    label: "Applications",
                                    data: applicationsByOffre.map((offre) => offre.totalApplications),
                                    backgroundColor: "#9966FF",
                                },
                            ],
                        }}
                        options={{
                            responsive: true,
                            plugins: {
                                legend: {display: false},
                                title: {display: true, text: "Applications by Offre"},
                            },
                        }}
                    />
                </div>

                {/* Applications by Type - Pie Chart */}
                <div className="mb-8 w-2/5 bg-white shadow-md rounded p-4">
                    <h2 className="text-xl font-semibold mb-4">Applications by Type</h2>
                    <Pie
                        data={{
                            labels: applicationsByType.map((type) => type.typeName),
                            datasets: [
                                {
                                    label: "Applications",
                                    data: applicationsByType.map((type) => type.totalApplications),
                                    backgroundColor: [
                                        "#4BC0C0", "#9966FF",
                                        "#36A2EB", "#FF6384", "#FFCE56"
                                    ],
                                },
                            ],
                        }}
                        options={{responsive: true, plugins: {legend: {position: "top"}}}}
                    />
                </div>

                {/* Applications by Étudiant - Bar Chart */}
                <div className="mb-8 w-1/2 bg-white shadow-md rounded p-4">
                    <h2 className="text-xl font-semibold mb-4">Applications by Étudiant</h2>
                    <Bar
                        data={{
                            labels: applicationsByEtudiant.map(
                                (etudiant) => `${etudiant.etudiantPrenom} ${etudiant.etudiantNom}`
                            ),
                            datasets: [
                                {
                                    label: "Applications",
                                    data: applicationsByEtudiant.map((etudiant) => etudiant.totalApplications),
                                    backgroundColor: "#673ab7",
                                },
                            ],
                        }}
                        options={{
                            responsive: true,
                            plugins: {
                                legend: {display: false},
                                title: {display: true, text: "Applications by Étudiant"},
                            },
                        }}
                    />
                </div>
            </div>
        </CFLayout>
    );
}
