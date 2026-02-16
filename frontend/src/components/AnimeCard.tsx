import type { Anime } from "../types/anime";
import "./AnimeCard.css"


interface Props {
    anime: Anime;
}

export function AnimeCard({ anime }: Props) {
    return (
        <div className="anime-card">
            <img src={anime.coverUrl} alt={anime.title} />
            <h3>{anime.title}</h3>
            <p>⭐ {anime.rating}</p>
            <p>{anime.year}</p>
            <p>{anime.description}</p>
        </div>
    );
}