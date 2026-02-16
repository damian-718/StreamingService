import { useAnime } from "../hooks/useAnime";
import { AnimeCard } from "../components/AnimeCard";

export function AnimeListPage() {
    const { anime, loading, error } = useAnime(); //triggers the useffect when this component mounts

    if (loading) return <p>Loading...</p>;
    if (error) return <p>Error: {error}</p>;

    // this component is a mapping of each anime to a animecard object
    // key is a special react prop so react can track which elements changed. unlike a prop passed from a parent to child, key is "passed" to react internally.
    // id is the UUID column from backend, unique identifier
    return (
        <div style={{ display: "flex", flexWrap: "wrap" }}>
            {anime.map(a => <AnimeCard key={a.id} anime={a} />)}
        </div>
    );
}