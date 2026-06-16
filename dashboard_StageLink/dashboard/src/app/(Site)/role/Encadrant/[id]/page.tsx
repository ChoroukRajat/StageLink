import { Metadata } from "next";
import EncadrantLayout from "@/components/Layouts/EncadrantLayout";



export const metadata: Metadata = {
    title: "StageLink",
    description:
        "This is Next.js Calender page for NextAdmin  Tailwind CSS Admin Dashboard Kit",
    // other metadata
};

const EnPage = () => {
    return (
        <EncadrantLayout>
            <div className="mx-auto max-w-7xl">
                <h1> WELCOME BACK SUPERVISOR</h1>
                <h3>How could we assist you today ?</h3>
            </div>
        </EncadrantLayout>
    );
};

export default EnPage;
