import { useState } from "react";
import type { Anime } from "../types/anime";
import { searchAnime } from "../api/animeApi";

export function useSearchAnime() {
    const [results, setResults] = useState<Anime[]>([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const search = async (query: string) => {
        if (!query) {
            setResults([]);
            return;
        }

        setLoading(true);
        setError(null);

        try {
            const data = await searchAnime(query);
            setResults(data);
        } catch (err: any) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    return { results, loading, error, search };
}