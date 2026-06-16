"use client"

import React, { useEffect, useState } from "react";
import { useEdgeStore } from "@/lib/edgestore";
import EtudiantLayout from "@/components/Layouts/EtudiantLayout";

import { jsPDF } from "jspdf";
import html2canvas from "html2canvas";

interface AttestationData {
    studentNom: string;
    studentPrenom: string;
    studentEmail: string;
    encadrantNom: string;
    encadrantPrenom: string;
    offreTitle: string;
    descriptionOffre: string;
    duree: string;
    objectifsProjet: string;
    remuneration: number;
    companyName: string;
    confirmationEtudiant: boolean;
    valide: boolean;
    evaluation: string;
    competence: string;
    type: string;
    adresseEntreprise: string;
    telephoneEntreprise: string;
    faxEntreprise: string;
    cfNom: string;
    cfPrenom: string;
    nomFiliere: string;
    nomEcole: string;
    telephoneEcole: string;
    adresseEcole: string;
    promotion: number;
}



type Candidature = {
    idCandidature: number;
    dateCreation: string;
    titreOffre: string;
    valide: boolean;
    confirmationEtudiant: boolean;
    rapportUrl: string;
};

type PopupProps = {
    candidature: Candidature | null;
    attestationData: AttestationData | null;
    onClose: () => void;
    onConfirm: () => void;
    onSubmitRapport: (rapport: File) => void;
};

const Popup: React.FC<PopupProps> = ({ candidature, attestationData, onClose, onConfirm, onSubmitRapport }) => {
    const [rapport, setRapport] = useState<File | null>(null);


    //hadchi zdto 3la 9bl generation

    const generatePDF = async () => {
        if (!attestationData) {
            alert("Data is still loading. Please try again later.");
            return;
        }

        const pdfContent = `
  <div class="bg-gray-100 p-6 min-h-screen">
    <div class="max-w-4xl mx-auto bg-white p-8 rounded-xl shadow-lg">
      <header class="text-center mb-8">
        <h1 class="text-3xl font-bold">${attestationData.evaluation ? 'Attestation de Stage' : 'Convention de Stage'}</h1>
        <p class="text-lg mt-2">Pour l'année académique ${attestationData.promotion}</p>
      </header>

      <section class="space-y-4">
        <div class="flex justify-between">
          <div>
            <h2 class="text-xl font-semibold">Informations Étudiant</h2>
            <p><strong>Nom:</strong> ${attestationData.studentNom} ${attestationData.studentPrenom}</p>
            <p><strong>Email:</strong> ${attestationData.studentEmail}</p>
          </div>

          <div>
            <h2 class="text-xl font-semibold">Informations Encadrant</h2>
            <p><strong>Nom:</strong> ${attestationData.encadrantNom} ${attestationData.encadrantPrenom}</p>
          </div>
        </div>

        <div>
          <h2 class="text-xl font-semibold">Informations de l'Entreprise</h2>
          <p><strong>Nom de l'entreprise:</strong> ${attestationData.companyName}</p>
          <p><strong>Adresse:</strong> ${attestationData.adresseEntreprise}</p>
          <p><strong>Téléphone:</strong> ${attestationData.telephoneEntreprise}</p>
          <p><strong>Fax:</strong> ${attestationData.faxEntreprise}</p>
        </div>

        <div>
          <h2 class="text-xl font-semibold">Informations sur le Stage</h2>
          <p><strong>Titre de l'offre:</strong> ${attestationData.offreTitle}</p>
          <p><strong>Description:</strong> ${attestationData.descriptionOffre}</p>
          <p><strong>Durée:</strong> ${attestationData.duree}</p>
          <p><strong>Objectifs du projet:</strong> ${attestationData.objectifsProjet}</p>
          <p><strong>Rémunération:</strong> ${attestationData.remuneration} DH</p>
          <p><strong>Type de stage:</strong> ${attestationData.type}</p>
          <p><strong>Compétence développée:</strong> ${attestationData.competence}</p>
          
        </div>

        <div>
          <h2 class="text-xl font-semibold">Informations École</h2>
          <p><strong>Nom de l'école:</strong> ${attestationData.nomEcole}</p>
          <p><strong>Adresse:</strong> ${attestationData.adresseEcole}</p>
          <p><strong>Téléphone:</strong> ${attestationData.telephoneEcole}</p>
        </div>

        <div>
          <h2 class="text-xl font-semibold">Informations du Coordinateur</h2>
          <p><strong>Nom:</strong> ${attestationData.cfNom} ${attestationData.cfPrenom}</p>
          <p><strong>Nom de la filière:</strong> ${attestationData.nomFiliere}</p>
        </div>

        <div class="mt-8">
          <p class="font-semibold">Confirmation de l'étudiant:</p>
          <p>${attestationData.confirmationEtudiant ? "Confirmé" : "Non Confirmé"}</p>
          <p class="font-semibold">Validation du stage:</p>
          <p>${attestationData.valide ? "Validé" : "Non Validé"}</p>
        </div>
        ${attestationData.evaluation ? `
        <div class="mt-8">
          <p class="font-semibold">Evaluation:</p>
          <p>${attestationData.evaluation}</p>
        </div>
        ` : ''}
      </section>
    </div>
  </div>
`;


        const element = document.createElement("div");
        element.innerHTML = pdfContent;
        document.body.appendChild(element);

        const canvas = await html2canvas(element);
        const imgData = canvas.toDataURL("image/png");
        const pdf = new jsPDF();
        const pdfWidth = pdf.internal.pageSize.getWidth();
        const pdfHeight = (canvas.height * pdfWidth) / canvas.width;

        pdf.addImage(imgData, "PNG", 0, 0, pdfWidth, pdfHeight);
        if(attestationData.evaluation == null){
            pdf.save("convention.pdf")
        }else{
            pdf.save("attestation.pdf");
        }


        document.body.removeChild(element);
    };


    const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.files && event.target.files[0]) {
            setRapport(event.target.files[0]);
        }
    };

    const handleRapportSubmit = (event: React.FormEvent) => {
        event.preventDefault();
        if (rapport) {
            onSubmitRapport(rapport);
        } else {
            alert("Please upload a file before submitting.");
        }
    };

    if (!candidature) return null;

    return (
        <div className="fixed inset-0 bg-gray-900 bg-opacity-50 flex justify-center items-center">
            <div className="bg-white p-6 rounded shadow-lg w-1/2">
                <h2 className="text-lg font-bold mb-4">{candidature.titreOffre}</h2>
                <p>Date of Creation: {new Date(candidature.dateCreation).toLocaleDateString()}</p>
                <p>Status: {candidature.valide ? "Valid" : "Not Valid"}</p>
                <p>Confirmation: {candidature.confirmationEtudiant ? "Confirmed" : "Not Confirmed"}</p>

                {candidature.valide && !candidature.confirmationEtudiant ? (
                    <button
                        onClick={onConfirm}
                        className="bg-green-500 text-white px-4 py-2 rounded mt-4"
                    >
                        Confirm Candidature
                    </button>
                ) : candidature.valide && candidature.confirmationEtudiant ? (
                    <>
                        {candidature.rapportUrl ? (
                            <a
                                href={candidature.rapportUrl}
                                download
                                className="bg-blue-500 text-white px-4 py-2 rounded mt-4 inline-block"
                            >
                                Download Rapport
                            </a>
                        ) : (
                            <form onSubmit={handleRapportSubmit} className="mt-4">
                                <label className="block mb-2">
                                    Upload Rapport: <br />
                                    <input type="file" onChange={handleFileChange} />
                                </label>
                                <button
                                    type="submit"
                                    className="bg-blue-500 text-white px-4 py-2 rounded"
                                >
                                    Submit Rapport
                                </button>
                            </form>
                        )}
                        <h3 className="mt-4">Télécharger Votre Document Attestation ou Convention de stage :</h3>
                        <button
                            onClick={generatePDF}
                            className="bg-blue-500 text-white px-4 py-2 rounded"
                        >
                            Télécharger
                        </button>
                    </>
                ) : null}


                <button
                    onClick={onClose}
                    className="bg-red-500 text-white px-4 py-2 rounded mt-4"
                >
                    Close
                </button>
            </div>
        </div>
    );
};

const CandidaturesPage: React.FC = () => {
    const [candidatures, setCandidatures] = useState<Candidature[]>([]);
    const [attestationData, setAttestationData] = useState<AttestationData | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [selectedCandidature, setSelectedCandidature] = useState<Candidature | null>(null);
    const Id = localStorage.getItem("idUtilisateur"); // Replace with the actual user ID
    const { edgestore } = useEdgeStore();

    useEffect(() => {
        // Fetch candidatures
        fetch(`http://localhost:8080/role/etudiant/${Id}/candidatures`)
            .then((response) => response.json())
            .then((data) => setCandidatures(data))
            .catch((error) => {
                console.error("Error fetching candidatures:", error);
            });
    }, [Id]);

    const handleRowClick  = async (candidature: Candidature) => {
        setSelectedCandidature(candidature);
        try {
            const response = await fetch(`http://localhost:8080/documents/${candidature.idCandidature}`); // Replace '1' with the dynamic ID
            if (!response.ok) {
                throw new Error("Failed to fetch attestation data");
            }
            const data: AttestationData = await response.json();
            setAttestationData(data);
        } catch (err: any) {
            setError(err.message || "An error occurred");
            console.error(err);
        }

    };

    if(selectedCandidature){
        console.log(selectedCandidature);
    }

    const handleConfirm = () => {
        if (!selectedCandidature) return;

        fetch(`http://localhost:8080/role/etudiant/${Id}/candidature/${selectedCandidature.idCandidature}?confirmationEtudiant=true`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },

        })
            .then((response) => {
                if (response.ok) {
                    alert("Candidature confirmed successfully!");
                    setSelectedCandidature(null);
                    // Reload candidatures
                    fetch(`http://localhost:8080/role/etudiant/${Id}/candidatures`)
                        .then((res) => res.json())
                        .then((data) => setCandidatures(data));
                } else {
                    throw new Error("Failed to confirm candidature");
                }
            })
            .catch((error) => {
                console.error("Error confirming candidature:", error);
            });
    };

    const handleSubmitRapport =async (rapport: File) => {
        if (!selectedCandidature) return;

        let res = await edgestore.publicFiles.upload({file: rapport});
        const rapportUrl = res.url;

        fetch(`http://localhost:8080/role/etudiant/${Id}/candidature/${selectedCandidature.idCandidature}?rapport=${rapportUrl}`, {
            method: "POST",

        })
            .then((response) => {
                if (response.ok) {
                    alert("Rapport submitted successfully!");
                    setSelectedCandidature(null);
                    // Reload candidatures
                    fetch(`http://localhost:8080/role/etudiant/${Id}/candidatures`)
                        .then((res) => res.json())
                        .then((data) => setCandidatures(data));
                } else {
                    throw new Error("Failed to submit rapport");
                }
            })
            .catch((error) => {
                console.error("Error submitting rapport:", error);
            });
    };

    return (
        <EtudiantLayout>
        <div className="p-6">
            <h1 className="text-2xl font-bold mb-4">Candidatures</h1>
            <table className="table-auto w-full border-collapse border border-gray-300">
                <thead>
                <tr className="bg-gray-200">
                    <th className="border border-gray-300 px-4 py-2">ID</th>
                    <th className="border border-gray-300 px-4 py-2">Date</th>
                    <th className="border border-gray-300 px-4 py-2">Title</th>
                    <th className="border border-gray-300 px-4 py-2">Status</th>
                </tr>
                </thead>
                <tbody>
                {candidatures.map((candidature) => (
                    <tr
                        key={candidature.idCandidature}
                        className="hover:bg-gray-100 cursor-pointer"
                        onClick={() => handleRowClick(candidature)}
                    >
                        <td className="border border-gray-300 px-4 py-2">{candidature.idCandidature}</td>
                        <td className="border border-gray-300 px-4 py-2">
                            {new Date(candidature.dateCreation).toLocaleDateString()}
                        </td>
                        <td className="border border-gray-300 px-4 py-2">{candidature.titreOffre}</td>
                        <td className="border border-gray-300 px-4 py-2">
                            {candidature.valide ? "Valid" : "Not Valid"}

                        </td>
                    </tr>
                ))}
                </tbody>
            </table>

            <Popup
                candidature={selectedCandidature}
                attestationData={attestationData}
                onClose={() => setSelectedCandidature(null)}
                onConfirm={handleConfirm}
                onSubmitRapport={handleSubmitRapport}
            />
        </div>
        </EtudiantLayout>
    );
};

export default CandidaturesPage;
