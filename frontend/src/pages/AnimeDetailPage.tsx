import { useParams } from "react-router-dom";
import { useAnimeDetail } from "../hooks/useAnimeDetail";
import { useEpisodes } from "../hooks/useEpisodes";
import { AnimeDetailCard } from "../components/AnimeDetailCard";
import { useEffect } from "react";

export function AnimeDetailPage() {
    const { id } = useParams<{ id: string }>();
    const { anime, loading: animeLoading, error } = useAnimeDetail(id!);

    // Hook for paginated episodes
    const { episodes, loadNextPage, hasMore, loading: episodesLoading } = useEpisodes(id!); // initializes variables to 0/empty

    // Load first page when anime id changes
    // ensured page0 loads automatically on mount
    useEffect(() => {
        loadNextPage();
    }, [id]);

    if (animeLoading) return <p>Loading anime...</p>;
    if (error) return <p>Error: {error}</p>;
    if (!anime) return <p>Anime not found</p>;

    return (
        <AnimeDetailCard
            anime={anime}
            episodes={episodes}
            loadNextPage={loadNextPage}
            hasMore={hasMore}
            episodesLoading={episodesLoading}
        />
    );
}