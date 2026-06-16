
import { Metadata } from "next";
import { redirect } from 'next/navigation';

export const metadata: Metadata = {
  title:
    "StageLink",
  description: "",
};

export default function Home() {
  redirect('/auth/signin');
}
