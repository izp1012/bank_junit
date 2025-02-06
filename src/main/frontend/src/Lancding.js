import React from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";

const LandingPage = () => {
    return (
        <div className="min-h-screen bg-gray-100 flex flex-col items-center justify-center">
            <header className="w-full p-4 bg-white shadow-md">
                <div className="container mx-auto flex justify-between items-center">
                    <h1 className="text-2xl font-bold text-gray-800">My Personal AI Assistant</h1>
                    <nav>
                        <ul className="flex space-x-4">
                            <li>
                                <a href="#features" className="text-gray-600 hover:text-gray-800">Features</a>
                            </li>
                            <li>
                                <a href="#about" className="text-gray-600 hover:text-gray-800">About</a>
                            </li>
                            <li>
                                <a href="#contact" className="text-gray-600 hover:text-gray-800">Contact</a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </header>

            <main className="flex flex-col items-center mt-10">
                <Card className="w-full max-w-2xl p-6 shadow-lg bg-white">
                    <CardContent>
                        <h2 className="text-4xl font-bold text-center text-gray-800">Welcome to Your Personal Assistant</h2>
                        <p className="text-center text-gray-600 mt-4">
                            Leverage the power of AI to streamline your tasks, answer your questions, and
                            improve productivity.
                        </p>
                        <div className="flex justify-center mt-6 space-x-4">
                            <Button className="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700">
                                Get Started
                            </Button>
                            <Button className="bg-gray-200 text-gray-800 px-6 py-2 rounded-lg hover:bg-gray-300">
                                Learn More
                            </Button>
                        </div>
                    </CardContent>
                </Card>
            </main>

            <footer className="w-full p-4 mt-10 bg-white shadow-md">
                <div className="container mx-auto text-center">
                    <p className="text-gray-600">&copy; 2025 My Personal AI Assistant. All rights reserved.</p>
                </div>
            </footer>
        </div>
    );
};

export default LandingPage;
