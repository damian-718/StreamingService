import type { Anime } from "../types/anime";
import { useNavigate } from "react-router-dom";
import "./AnimeCard.css"

// typescript way of doing type checking on the prop
interface Props {
    anime: Anime;
}
// anime is passed from the parent (page) to here. 
export function AnimeCard({ anime }: Props) {
    const navigate = useNavigate();

    const handleClick = () => {
        // Navigate to /anime/:id
        navigate(`/anime/${anime.id}`);
    };

    return (
        <div className="anime-card" onClick={handleClick}>
            <img src={anime.coverUrl} alt={anime.title} />
            <h2>{anime.rating}</h2>
            <h3>{anime.title}</h3>
        </div>
    );
}