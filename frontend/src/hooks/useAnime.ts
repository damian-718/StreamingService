import { useState, useEffect } from "react";
import type { Anime } from "../types/anime";
import { fetchAllAnime } from "../api/animeApi";

export function useAnime() {
    const [anime, setAnime] = useState<Anime[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => { // updates state variable in the hook, a state change retriggers this
        fetchAllAnime() // backend calls returns a promise, then means it will update when the promise returns a value
            .then(setAnime)
            .catch(err => setError(err.message)) // built in promise method to catch errors
            .finally(() => setLoading(false)); // built-in promise method, run this regardless if promise passed or failed (loading state goes to false)
    }, []); // run only once on mount

    return { anime, loading, error };
}