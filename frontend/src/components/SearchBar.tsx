import { useState } from "react";

interface Props {
    onSearch: (query: string) => void;
}

export function SearchBar({ onSearch }: Props) {
    const [query, setQuery] = useState("");

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setQuery(e.target.value);
        onSearch(e.target.value);
    };

    return (
        <input
            type="text"
            value={query}
            onChange={handleChange}
            placeholder="Search anime or episode..."
            style={{ width: "100%", padding: "8px", fontSize: "16px" }}
        />
    );
}