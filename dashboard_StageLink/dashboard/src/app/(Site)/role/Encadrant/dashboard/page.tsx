"use client";

import React, { useEffect, useState } from "react";
import { Pie, Bar } from "react-chartjs-2";
import {
    Chart as ChartJS,
    ArcElement,
    Tooltip,
    Legend,
    CategoryScale,
    LinearScale,
    BarElement,
} from "chart.js";
import EncadrantLayout from "@/components/Layouts/EncadrantLayout";

// Register chart.js components
ChartJS.register(ArcElement, Tooltip, Legend, CategoryScale, LinearScale, BarElement);

const DashboardPage = () => {
    const [studentsPerOffer, setStudentsPerOffer] = useState<any[]>([]);
    const [validatedApplications, setValidatedApplications] = useState<any[]>([]);
    const [evaluatedApplications, setEvaluatedApplications] = useState<any[]>([]);
    const [studentsDistribution, setStudentsDistribution] = useState<any[]>([]);
    const encadrantId = localStorage.getItem("id"); // Example Encadrant ID, replace with dynamic ID as needed

    // Colors for the charts
    const colors = ["#36A2EB", "#FF6384", "#FFCE56", "#4BC0C0", "#9966FF"];

    // Fetch data from APIs
    useEffect(() => {
        const fetchData = async () => {
            try {
                const [studentsRes, validatedRes, evaluatedRes, distributionRes] = await Promise.all([
                    fetch(`http://localhost:8080/dashboard/en/students-per-offer/${encadrantId}`).then((res) => res.json()),
                    fetch(`http://localhost:8080/dashboard/en/validated-applications/${encadrantId}`).then((res) => res.json()),
                    fetch(`http://localhost:8080/dashboard/en/evaluated-applications/${encadrantId}`).then((res) => res.json()),
                    fetch(`http://localhost:8080/dashboard/en/students-distribution/${encadrantId}`).then((res) => res.json()),
                ]);

                setStudentsPerOffer(studentsRes);
                setValidatedApplications(validatedRes);
                setEvaluatedApplications(evaluatedRes);
                setStudentsDistribution(distributionRes);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };

        fetchData();
    }, [encadrantId]);

    // Helper to prepare bar chart data
    const prepareBarChartData = (data: any[], label: string, countKey: string) => ({
        labels: data.map((item) => item.offerTitle || "Unnamed Offer"),
        datasets: [
            {
                label,
                data: data.map((item) => item[countKey]),
                backgroundColor: colors,
                borderColor: colors.map((color) => color + "AA"),
                borderWidth: 1,
            },
        ],
    });

    // Helper to prepare pie chart data
    const preparePieChartData = (data: any[], labelKey: string, countKey: string) => ({
        labels: data.map((item) => item[labelKey]),
        datasets: [
            {
                data: data.map((item) => item[countKey]),
                backgroundColor: colors,
            },
        ],
    });

    return (
        <EncadrantLayout>
            <div style={{padding: "20px"}}>
                <h1 className="text-2xl font-bold mb-6">Encadrant Dashboard</h1>

                {/* Students Per Offer - Bar Chart */}
                <div style={{marginBottom: "30px"}}>
                    <h2 className="text-xl font-semibold mb-4">Students Per Offer</h2>
                    {studentsPerOffer.length > 0 ? (
                        <Bar
                            data={prepareBarChartData(studentsPerOffer, "Number of Students", "studentCount")}
                            options={{responsive: true}}
                        />
                    ) : (
                        <p>No data available for students per offer.</p>
                    )}
                </div>

                {/* Validated Applications - Bar Chart */}
                <div style={{marginBottom: "30px"}}>
                    <h2 className="text-xl font-semibold mb-4">Validated Applications Per Offer</h2>
                    {validatedApplications.length > 0 ? (
                        <Bar
                            data={prepareBarChartData(validatedApplications, "Validated Applications", "validatedCount")}
                            options={{responsive: true}}
                        />
                    ) : (
                        <p>No data available for validated applications.</p>
                    )}
                </div>

                {/* Evaluated Applications - Bar Chart */}
                <div style={{marginBottom: "30px"}}>
                    <h2 className="text-xl font-semibold mb-4">Evaluated Applications Per Offer</h2>
                    {evaluatedApplications.length > 0 ? (
                        <Bar
                            data={prepareBarChartData(evaluatedApplications, "Evaluated Applications", "evaluatedCount")}
                            options={{responsive: true}}
                        />
                    ) : (
                        <p>No data available for evaluated applications.</p>
                    )}
                </div>

                <h2 className="text-xl font-semibold mb-4">Students Distribution By School</h2>

                {/* Students Distribution - Pie Chart */}
                <div className="w-1/2 justify-self-center">
                    {studentsDistribution.length > 0 ? (
                        <Pie
                            data={preparePieChartData(studentsDistribution, "schoolName", "studentCount")}
                            options={{responsive: true}}
                        />
                    ) : (
                        <p>No data available for students distribution by school.</p>
                    )}
                </div>
            </div>
        </EncadrantLayout>
    );
};

export default DashboardPage;
