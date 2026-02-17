import type { Anime } from "../types/anime";
import type { Episode } from "../types/episode";
import "./AnimeDetailCard.css";

interface Props {
    anime: Anime;
    episodes: Episode[];              // new prop
    loadNextPage: () => void;         // to fetch more episodes
    hasMore: boolean;                 // whether more pages exist
    episodesLoading: boolean;         // loading state
}

export function AnimeDetailCard({
    anime,
    episodes,
    loadNextPage,
    hasMore,
    episodesLoading,
}: Props) {
    // scroll handler for infinite scroll
    const handleScroll = (e: React.UIEvent<HTMLDivElement>) => {
        const target = e.target as HTMLDivElement;
        // scrollHeight is total height of scrollable content, scrolltop is how far user has scrolled from the top. 
        // clientheight is visible height of the scroller, so when we scroll down to episode 20, thats the bottom, triggering api call to get next 20
        if (target.scrollHeight - target.scrollTop <= target.clientHeight + 50) { // added + 50 padding to trigger pagination more loosely
            // trigger when near bottom
            if (hasMore && !episodesLoading) loadNextPage();
        }
    };

    // episodes is a container made scrollable via css file.
    // div container is scrollable automatically and has builtin onscroll event
    return (
        <div className="anime-detail">
            <img src={anime.coverUrl} alt={anime.title} />
            <div className="detail-content">
                <h2>{anime.title}</h2>
                <p>⭐ {anime.rating}</p>
                <p>{anime.year}</p>
                <p>{anime.description}</p>

                <div className="episodes" onScroll={handleScroll}>
                    <h3>Episodes</h3>
                    <ul>
                        {episodes.map((ep, index) => (
                            <li key={`${ep.id}-${index}`} className="episode-item">
                                <span className="ep-number">Ep {ep.number}</span>
                                <span className="ep-title">{ep.title}</span>
                            </li>
                        ))}
                    </ul>

                    {episodesLoading && <p>Loading more episodes...</p>}
                    {!hasMore && <p>All episodes loaded</p>}
                </div>
            </div>
        </div>
    );
}