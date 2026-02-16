import type { Anime } from "../types/anime";

interface Props {
    anime: Anime;
}

export function AnimeDetailCard({ anime }: Props) {
    return (
        <div className="anime-detail">
            <img src={anime.coverUrl} alt={anime.title} />
            <h2>{anime.title}</h2>
            <p>⭐ {anime.rating}</p>
            <p>{anime.year}</p>
            <p>{anime.description}</p>

            <h3>Episodes</h3>
            <ul>
                {anime.episodes.map(ep => (
                    <li key={ep.id}>
                        {ep.number}. {ep.title}
                    </li>
                ))}
            </ul>
        </div>
    );
}