// Header.jsx
import React from "react";

const Header = () => {
    return (
        <header className="bg-white shadow p-4 flex justify-between items-center">
            <div className="text-xl font-bold">Personal Assistant</div>
            <nav className="flex gap-4">
                <a href="#features" className="text-gray-600 hover:text-gray-900">Features</a>
                <a href="#about" className="text-gray-600 hover:text-gray-900">About</a>
                <a href="#contact" className="text-gray-600 hover:text-gray-900">Contact</a>
            </nav>
        </header>
    );
};

export default Header;
