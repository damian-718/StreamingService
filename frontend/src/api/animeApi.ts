// this file is for api calls
import type { Anime } from "../types/anime";

const API_URL = import.meta.env.VITE_API_URL;

export async function fetchAllAnime(): Promise<Anime[]> { // backend returns array of anime objects
    const res = await fetch(`${API_URL}/anime`);
    if (!res.ok) throw new Error("Failed to fetch anime");
    return res.json();
}

// fetch specific anime by id
export async function fetchAnime(id: string): Promise<Anime> { // backend returns array of anime objects
    const res = await fetch(`${API_URL}/anime/${id}`);
    if (!res.ok) throw new Error("Failed to fetch anime");
    return res.json();
}

export async function importTopAnime(): Promise<void> {
    const res = await fetch(`${API_URL}/anime/import-top`, {
        method: "POST"
    });
    if (!res.ok) throw new Error("Failed to import top anime");
}