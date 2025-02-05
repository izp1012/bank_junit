import React, { useState, useEffect, useRef } from 'react';
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
const Chat = () => {
    const [messages, setMessages] = useState([]);
    const [inputMessage, setInputMessage] = useState('');
    const [socket, setSocket] = useState(null);
    const messagesEndRef = useRef(null);
    const [stompClient, setStompClient] = useState(null);

    useEffect(() => {
        // 웹소켓 연결
        // const ws = new WebSocket('ws://localhost:8081/ws-chat');
        const ws = new SockJS("http://localhost:8080/chat");
        const client = new Client({
            webSocketFactory: () => socket,
            onConnect: () => {
                console.log("Connected to WebSocket");
                client.subscribe("/topic/chat", (message) => {
                    setMessages((prevMessages) => [...prevMessages, message.body]);
                });
            },
            debug: (str) => {
                console.log(str);
            },
        });
        setStompClient(client);
        client.activate(); // 연결 시작
        setSocket(ws);

        ws.onopen = () => {
            console.log("✅ WebSocket 연결 성공");
        };

        ws.onmessage = (event) => {
            console.log("📩 서버에서 받은 메시지:", event.data);
            const message = JSON.parse(event.data);
            setMessages(prev => [...prev, message]);
        };

        ws.onerror = (error) => {
            console.error("❌ WebSocket 에러 발생:", error);
        };

        ws.onclose = () => {
            console.log("🔌 WebSocket 연결 종료");
        };



        return () => {
            ws.close();
            client.deactivate(); // 연결 종료}
        }

    }, []);

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
    };

    useEffect(scrollToBottom, [messages]);

    const sendMessage = (e) => {
        e.preventDefault();
        if (inputMessage.trim() && socket) {
            const message = {
                content: inputMessage,
                sender: 'User', // 실제 사용 시 인증 정보 사용
                timestamp: new Date().toISOString()
            };
            stompClient.send("/app/sendMessage", {}, message);
            setInputMessage('');
        }
    };

    return (
        <div style={styles.container}>
            <div style={styles.chatWindow}>
                <div style={styles.messages}>
                    {messages.map((msg, idx) => (
                        <div key={idx} style={styles.messageBubble}>
                            <div style={styles.messageSender}>{msg.sender}</div>
                            <div style={styles.messageContent}>{msg.content}</div>
                            <div style={styles.messageTime}>
                                {new Date(msg.timestamp).toLocaleTimeString()}
                            </div>
                        </div>
                    ))}
                    <div ref={messagesEndRef} />
                </div>
                <form onSubmit={sendMessage} style={styles.inputArea}>
                    <input
                        type="text"
                        value={inputMessage}
                        onChange={(e) => setInputMessage(e.target.value)}
                        style={styles.inputField}
                        placeholder="메시지를 입력하세요..."
                    />
                    <button type="submit" style={styles.sendButton}>전송</button>
                </form>
            </div>
        </div>
    );
};

// 다크 테마 스타일
const styles = {
    container: {
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        height: '100vh',
        backgroundColor: '#1a1a1a',
    },
    chatWindow: {
        width: '400px',
        height: '600px',
        backgroundColor: '#2d2d2d',
        borderRadius: '10px',
        display: 'flex',
        flexDirection: 'column',
    },
    messages: {
        flex: 1,
        padding: '20px',
        overflowY: 'auto',
        color: '#ffffff',
    },
    messageBubble: {
        backgroundColor: '#3a3a3a',
        borderRadius: '8px',
        padding: '10px',
        marginBottom: '10px',
    },
    messageSender: {
        fontWeight: 'bold',
        color: '#4d9fec',
        marginBottom: '4px',
    },
    messageContent: {
        fontSize: '14px',
    },
    messageTime: {
        fontSize: '12px',
        color: '#a0a0a0',
        textAlign: 'right',
    },
    inputArea: {
        display: 'flex',
        padding: '20px',
        borderTop: '1px solid #3a3a3a',
    },
    inputField: {
        flex: 1,
        padding: '10px',
        borderRadius: '5px',
        border: 'none',
        marginRight: '10px',
        backgroundColor: '#3a3a3a',
        color: '#ffffff',
    },
    sendButton: {
        padding: '10px 20px',
        backgroundColor: '#4d9fec',
        border: 'none',
        borderRadius: '5px',
        color: 'white',
        cursor: 'pointer',
    },
};

export default Chat;