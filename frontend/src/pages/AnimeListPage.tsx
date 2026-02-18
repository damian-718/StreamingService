import { useAnime } from "../hooks/useAnime";
import { AnimeCard } from "../components/AnimeCard";
import "./AnimeListPage.css";
import { useSearchAnime } from "../hooks/useSearchAnime";
import { SearchBar } from "../components/SearchBar";

export function AnimeListPage() {
    const { anime, loading, error } = useAnime(); //triggers the useffect when this component mounts
    const { results, loading: searchLoading, error: searchError, search } = useSearchAnime();
    const displayAnime = results.length ? results : anime;

    if (loading) return <p>Loading...</p>;
    if (error) return <p>Error: {error}</p>;

    // this component is a mapping of each anime to a animecard object
    // key is a special react prop so react can track which elements changed. unlike a prop passed from a parent to child, key is "passed" to react internally.
    // id is the UUID column from backend, unique identifier
    return (
        <div className="anime-section">
            <h2>Popular Anime</h2>
            <SearchBar onSearch={search} />

            {loading || searchLoading ? <p>Loading...</p> : null}
            {error || searchError ? <p>Error: {error || searchError}</p> : null}

            <div className="anime-scroll-container">
                {displayAnime.map(a => (
                    <AnimeCard key={a.id} anime={a} />
                ))}
            </div>
        </div>
    );
}