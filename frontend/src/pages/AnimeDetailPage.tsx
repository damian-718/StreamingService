import { useParams } from "react-router-dom";
import { useAnimeDetail } from "../hooks/useAnimeDetail";
import { AnimeDetailCard } from "../components/AnimeDetailCard";


export function AnimeDetailPage() {
    const { id } = useParams<{ id: string }>();
    const { anime, loading, error } = useAnimeDetail(id!); // id! is a non null assertion for typescript, id will always have a value


    if (loading) return <p>Loading...</p>;
    if (error) return <p>Error: {error}</p>;
    if (!anime) return <p>Anime not found</p>;

    return <AnimeDetailCard anime={anime} />;
}