import { Metadata } from "next";
import EtudiantLayout from "@/components/Layouts/EtudiantLayout";



export const metadata: Metadata = {
    title: "StageLink",
    description:
        "This is Next.js Calender page for NextAdmin  Tailwind CSS Admin Dashboard Kit",
    // other metadata
};

const EnPage = () => {
    return (
        <EtudiantLayout>
            <div className="mx-auto max-w-7xl">
                <h1> WELCOME BACK STUDENT</h1>
                <h3>How could we assist you today ?</h3>
            </div>
        </EtudiantLayout>
    );
};

export default EnPage;
