import type { Episode } from "./episode";
export interface Anime {
    id: string;
    malId: number;
    title: string;
    description: string;
    rating: number;
    year: number;
    coverUrl: string;
    episodes: Episode[];
}