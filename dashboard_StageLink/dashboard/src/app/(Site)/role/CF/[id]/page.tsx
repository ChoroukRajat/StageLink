import { Metadata } from "next";
import CFLayout from "@/components/Layouts/CFLayout";



export const metadata: Metadata = {
    title: "StageLink",
    description:
        "This is Next.js Calender page for NextAdmin  Tailwind CSS Admin Dashboard Kit",
    // other metadata
};

const CfPage = () => {
    return (
        <CFLayout>
            <div className="mx-auto max-w-7xl">
                <h1 className="text-gray-7"> WELCOME BACK CHEF</h1>
                <h3>How could we assist you today ?</h3>
            </div>
        </CFLayout>
    );
};

export default CfPage;
