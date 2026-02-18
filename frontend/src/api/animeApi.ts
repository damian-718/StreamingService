// this file is for api calls
import type { Anime } from "../types/anime";
import type { Episode } from "../types/episode";

const API_URL = import.meta.env.VITE_API_URL;


export async function fetchAnimeEpisodes(
    animeId: string,
    page: number = 0,
    size: number = 20
): Promise<{ content: Episode[]; last: boolean }> {
    const res = await fetch(`${API_URL}/anime/${animeId}/episodes?page=${page}&size=${size}`);
    if (!res.ok) throw new Error("Failed to fetch episodes");

    // Backend returns a Spring Page object:
    // { content: Episode[], totalElements, totalPages, last, ... }
    return res.json();
}

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

// elasticsearch
export async function searchAnime(query: string): Promise<Anime[]> {
    const res = await fetch(`${API_URL}/anime/search?q=${encodeURIComponent(query)}`);
    if (!res.ok) throw new Error("Failed to search anime");
    return res.json();
}