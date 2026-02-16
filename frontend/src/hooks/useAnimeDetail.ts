import { useState, useEffect } from "react";
import type { Anime } from "../types/anime";
import { fetchAnime } from "../api/animeApi";

export function useAnimeDetail(id: string) {
    const [anime, setAnime] = useState<Anime | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (!id) return;
        fetchAnime(id) // backend calls returns a promise, then means it will update when the promise returns a value
            .then(setAnime)
            .catch(err => setError(err.message)) // built in promise method to catch errors
            .finally(() => setLoading(false));
    }, [id]);

    return { anime, loading, error };
}