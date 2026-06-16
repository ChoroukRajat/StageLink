'use client'; // Marking as Client Component to use hooks

import { useState, useEffect } from 'react';
import { useParams } from 'next/navigation';
import { User } from "@/models/User";

const UserProfile = () => {
  const { id } = useParams();
  const [userData, setUserData] = useState<User | null>(null);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        // Retrieve the token from localStorage
        const token = localStorage.getItem('token');
        if (!token) {
          throw new Error('No token found. Please log in again.');
        }

        // Make the API call with the token in the Authorization header
        const response = await fetch(`/api/user/${id}`, {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        });

        if (!response.ok) {
          throw new Error('Failed to fetch user data');
        }

        const data = await response.json();
        setUserData(data);
      } catch (err: any) {
        setError(err.message);
      }
    };

    if (id) {
      fetchUserData();
    }
  }, [id]);

  // Display the error message if any
  if (error) {
    return <p>{error}</p>;
  }

  // Display a loading message while data is being fetched
  if (!userData) {
    return <p>Loading...</p>;
  }

  // Display user data if successfully fetched
  return (
    <div>
      <h1>User Profile</h1>
      <p><strong>First Name:</strong> {userData.prenom}</p>
      <p><strong>Last Name:</strong> {userData.nom}</p>
      <p><strong>Email:</strong> {userData.email}</p>
    </div>
  );
};

export default UserProfile;
