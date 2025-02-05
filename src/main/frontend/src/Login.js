import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleLogin = (e) => {
        e.preventDefault();

        // 간단한 로그인 검증 (실제 환경에서는 API 호출 필요)
        if (username === "testuser" && password === "password123") {
            alert("로그인 성공!");
            navigate("/chat"); // 로그인 성공 시 채팅 페이지로 이동
        } else {
            alert("로그인 실패! 올바른 정보를 입력하세요.");
        }
    };

    return (
        <div className="p-4 max-w-md mx-auto">
            <h2 className="text-xl font-bold mb-4">로그인</h2>
            <form onSubmit={handleLogin} className="space-y-4">
                <div>
                    <label className="block text-sm font-medium">사용자 이름</label>
                    <input
                        type="text"
                        className="border p-2 w-full"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label className="block text-sm font-medium">비밀번호</label>
                    <input
                        type="password"
                        className="border p-2 w-full"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className="bg-blue-500 text-white px-4 py-2 w-full">
                    로그인
                </button>
            </form>
        </div>
    );
};

export default Login;
