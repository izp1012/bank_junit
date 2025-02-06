// components/Button.jsx
import React from "react";
import clsx from "clsx";

export function Button({ children, variant = "default", className, ...props }) {
    const variants = {
        default: "bg-blue-500 text-white hover:bg-blue-600",
        outline: "border border-blue-500 text-blue-500 hover:bg-blue-100",
    };

    return (
        <button
            className={clsx(
                "px-4 py-2 rounded-md font-medium focus:outline-none",
                variants[variant],
                className
            )}
            {...props}
        >
            {children}
        </button>
    );
}
