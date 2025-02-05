import React, {useEffect, useState} from 'react';
import axios from 'axios';
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
const SignupForm = () => {
    // 상태 관리
    const [formData, setFormData] = useState({
        username: '',
        password: '',
        email: '',
        fullname : ''
    });

    const [hello, setHello] = useState('')
    const [errors, setErrors] = useState({});
    const [isSubmitting, setIsSubmitting] = useState(false); // 로딩 상태 추가
    // 입력값 변경 핸들러
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    // 유효성 검사
    const validateForm = () => {
        const newErrors = {};
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (!formData.username.trim()) {
            newErrors.username = '사용자명을 입력해주세요';
        }

        if (!formData.password) {
            newErrors.password = '비밀번호를 입력해주세요';
        } else if (formData.password.length < 6) {
            newErrors.password = '비밀번호는 6자 이상이어야 합니다';
        }

        if (!formData.email) {
            newErrors.email = '이메일을 입력해주세요';
        } else if (!emailRegex.test(formData.email)) {
            newErrors.email = '유효한 이메일 형식이 아닙니다';
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    // 폼 제출 핸들러
    const handleSubmit = async (e) => {
        console.log('폼 제출 데이터:', formData);
        e.preventDefault();

        if (validateForm()) {
            // 유효성 검사 통과 시 처리 (예: API 호출)

            try {
                const response = await axios.post('/api/api/join', formData, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }
                );
                console.log(response);
                if (response.status === 201) {
                    alert('회원가입 성공!');
                    setFormData({ username: '', password: '', email: '', fullname : ''}); // 폼 초기화
                }
            } catch (error) {

                console.error('회원가입 실패:', error);
                if (error.response) {
                    // 서버에서 에러 응답이 온 경우
                    alert(`회원가입 실패: ${error.response.data.msg}`);
                } else {
                    alert('네트워크 오류가 발생했습니다.');
                }
            } finally {
                setIsSubmitting(false); // 요청 종료
            }
            // 초기화
            // setFormData({ username: '', password: '', email: '', fullname: ''});
        }
    };

    return (
        <div style={styles.container}>
            {hello}
            <h2 style={styles.title}>회원가입</h2>
            <form onSubmit={handleSubmit} style={styles.form}>
                <div style={styles.formGroup}>
                    <label style={styles.label}>
                        사용자명:
                        <input
                            type="text"
                            name="username"
                            value={formData.username}
                            onChange={handleChange}
                            style={styles.input}
                            placeholder="사용자명을 입력하세요"
                        />
                    </label>
                    {errors.username && <span style={styles.error}>{errors.username}</span>}
                </div>

                <div style={styles.formGroup}>
                    <label style={styles.label}>
                        비밀번호:
                        <input
                            type="password"
                            name="password"
                            value={formData.password}
                            onChange={handleChange}
                            style={styles.input}
                            placeholder="6자 이상 입력하세요"
                        />
                    </label>
                    {errors.password && <span style={styles.error}>{errors.password}</span>}
                </div>

                <div style={styles.formGroup}>
                    <label style={styles.label}>
                        이메일:
                        <input
                            type="email"
                            name="email"
                            value={formData.email}
                            onChange={handleChange}
                            style={styles.input}
                            placeholder="example@domain.com"
                        />
                    </label>
                    {errors.email && <span style={styles.error}>{errors.email}</span>}
                </div>

                <div style={styles.formGroup}>
                    <label style={styles.label}>
                        FullName:
                        <input
                            type="text"
                            name="fullname"
                            value={formData.fullname}
                            onChange={handleChange}
                            style={styles.input}
                            placeholder="John Doe"
                        />
                    </label>
                    {errors.fullname && <span style={styles.error}>{errors.fullname}</span>}
                </div>

                <button type="submit" style={styles.button}>
                    가입하기
                </button>
            </form>
        </div>
    );
};

// 스타일 정의
const styles = {
    container: {
        maxWidth: '400px',
        margin: '2rem auto',
        padding: '2rem',
        boxShadow: '0 0 10px rgba(0,0,0,0.1)',
        borderRadius: '8px'
    },
    title: {
        textAlign: 'center',
        color: '#333'
    },
    form: {
        display: 'flex',
        flexDirection: 'column',
        gap: '1rem'
    },
    formGroup: {
        display: 'flex',
        flexDirection: 'column',
        gap: '0.5rem'
    },
    label: {
        fontWeight: 'bold',
        color: '#555'
    },
    input: {
        padding: '0.8rem',
        border: '1px solid #ddd',
        borderRadius: '4px',
        fontSize: '1rem'
    },
    error: {
        color: 'red',
        fontSize: '0.8rem'
    },
    button: {
        padding: '1rem',
        backgroundColor: '#007bff',
        color: 'white',
        border: 'none',
        borderRadius: '4px',
        cursor: 'pointer',
        fontSize: '1rem',
        marginTop: '1rem'
    }
};

export default SignupForm;