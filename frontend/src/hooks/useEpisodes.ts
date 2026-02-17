import { useState, useEffect, useRef, useCallback } from "react";
import { fetchAnimeEpisodes } from "../api/animeApi";
import type { Episode } from "../types/episode";

export function useEpisodes(animeId: string) {
    const [episodes, setEpisodes] = useState<Episode[]>([]);
    const [page, setPage] = useState(0);
    const [hasMore, setHasMore] = useState(true);
    const [loading, setLoading] = useState(false);
    const fetchingRef = useRef(false); // prevents duplicate fetches

    // initial base state hook
    useEffect(() => {
        // reset state when anime changes
        setEpisodes([]);
        setPage(0);
        setHasMore(true);
    }, [animeId]);

    // this is a seperate useeffect to actually load page of episodes, 0,1,2, etc...
    const loadNextPage = useCallback(async () => {
        if (!hasMore || fetchingRef.current) return; // prevent duplicate calls
        fetchingRef.current = true;
        setLoading(true);

        try {
            const res = await fetchAnimeEpisodes(animeId, page, 20);
            setEpisodes(prev => [...prev, ...res.content]);
            setHasMore(!res.last);
            setPage(prev => prev + 1);
        } catch (err: any) {
            console.error(err.message);
        } finally {
            setLoading(false);
            fetchingRef.current = false;
        }
    }, [animeId, page, hasMore]);

    return { episodes, loadNextPage, hasMore, loading };
}