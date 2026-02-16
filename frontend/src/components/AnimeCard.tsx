import type { Anime } from "../types/anime";

interface Props {
    anime: Anime;
}

export function AnimeCard({ anime }: Props) {
    return (
        <div style={{ border: "1px solid #ccc", padding: "1rem", margin: "1rem" }}>
            <img src={anime.coverUrl} width={150} alt={anime.title} />
            <h3>{anime.title}</h3>
            <p>⭐ {anime.rating}</p>
            <p>{anime.year}</p>
            <p>{anime.description}</p>
        </div>
    );
}