import React, { useState, useEffect, useRef } from 'react';
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import {Stomp} from "@stomp/stompjs";

const Chat = () => {
    const [messages, setMessages] = useState([]);
    const [input, setInput] = useState("");
    const [socket, setSocket] = useState(null);
    const messagesEndRef = useRef(null);
    const [stompClient, setStompClient] = useState(null);

    useEffect(() => {
        // STOMP 클라이언트 생성
        // const client = new Client({
        //     brokerURL: "ws://localhost:8081/chat", // Spring Boot WebSocket 엔드포인트
        //     reconnectDelay: 5000, // 자동 재연결 설정 (5초)
        //     heartbeatIncoming: 4000,
        //     heartbeatOutgoing: 4000
        // });

        const socket = new SockJS("http://localhost:8081/chat"); // 1️⃣ WebSocket 연결
        const client = Stomp.over(socket);


        client.onConnect = function (frame) {
            console.log("✅ STOMP 연결 성공");

            // 메시지 구독
            client.subscribe("/topic/public", (message) => {
                const receivedMessage = JSON.parse(message.body);
                setMessages((prev) => [...prev, receivedMessage]);
            });
        };
        client.onStompError = (frame) => {
            console.error("❌ STOMP 오류 발생:")
            console.log('Broker reported error: ' + frame.headers['message']);
            console.log('Additional details: ' + frame.body);
        };
        client.debug = (str ) => {
            console.debug("STOMP debug :", str)
        }
        client.activate();
        setStompClient(client);

        return () => {
            client.deactivate();
        };
    }, []);

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
    };

    useEffect(scrollToBottom, [messages]);

    // const sendMessage = () => {
    //     if (stompClient && stompClient.connected && input.trim()) {
    //         const message = { sender: "User1", text: input };
    //
    //         // stompClient.publish({
    //         //     destination: "/chat",
    //         //     body: JSON.stringify(message),
    //         // });
    //         stompClient.send("/app/chat.sendMessage", {}, JSON.stringify({ text: input }));
    //
    //         setInput("");
    //     } else {
    //         console.error("❌ STOMP 연결이 활성화되지 않았습니다.");
    //     }
    // };
    const sendMessage = (e) => {
        e.preventDefault(); // 폼 제출 시 새로고침 방지

        if (stompClient && stompClient.connected && input.trim()) {
            const message = {
                sender: "User1",
                content: input,
                timestamp: new Date().toISOString()
            };

            stompClient.send("/app/chat.sendMessage", {}, JSON.stringify({ text: input }));

            // 로컬 메시지 목록에 즉시 추가
            setMessages(prevMessages => [...prevMessages, message]);
            setInput("");
        } else {
            console.error("❌ STOMP 연결이 활성화되지 않았습니다.");
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
                        value={input}
                        onChange={(e) => setInput(e.target.value)}
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