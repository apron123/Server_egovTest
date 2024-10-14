/*
  Axios 라이브러리 사용 중
  사용 예시
      axiosRequest(method, url, requestData)
      .then(function (data) {
        console.log("Success:", data);
        //요청 성공 시
        성공시 사용할 함수(data);
      })
      .catch(function (errMsgData) {
        //요청 실패 시
        alert(errMsgData.result);
      });

*/
/*
  Axios interceptor를 사용하여 request에 access token 삽입
*/
axios.interceptors.request.use(
  function (config) {
    const token = window.localStorage.getItem("accessToken");
    if (token) {
      config.headers["Authorization"] = "Bearer " + token;
    }
    return config;
  },
  function (error) {
    return Promise.reject(error);
  }
);

/* 
    access token이 만료시 refresh token으로 token 재발급을 요청
  */
async function refreshAccessToken() {
  try {
    const refreshToken = window.localStorage.getItem("refreshToken");
    const response = await axios.post("/user/refresh", {
      refresh_token: refreshToken,
    });
    const newAccessToken = response.data.result_data.access_token;
    const newRefreshToken = response.data.result_data.refresh_token;
    window.localStorage.setItem("accessToken", newAccessToken);
    window.localStorage.setItem("refreshToken", newRefreshToken);
    return {
      accessToken: newAccessToken,
      refreshToken: newRefreshToken,
    };
  } catch (error) {
    /* refreshToken과 accessToken이 모두 만료되었을 경우 Logout 처리 */

    console.log(error);

    if (
      (error.response && error.response.status === 401) ||
      (error.response.data.result_code == 400 &&
        error.response.data.result_msg == "Jwt token이 올바르지 않습니다.")
    ) {
      window.localStorage.clear();
      alert("인증이 만료되어 로그아웃 되었습니다");
      // login 화면으로 리다이렉션 추가
      window.location.href = "/view/account/login";
    } else {
      throw new Error("Failed to refresh access token");
    }
  }
}

/*
    Axios 를 사용하여 Http 요청
  */

function axiosRequest(method, url, data = null) {
  // Axios 요청 설정
  const config = {
    method: method,
    url: "/" + url,
    data: data,
  };

  // Axios 요청을 Promise로 반환
  return axios(config)
    .then(function (response) {
      // 성공 시 response.data 반환
      return response.data;
    })
    .catch(async function (error) {
      // 엑세스 토큰이 만료된 경우
      if (error.response && error.response.status === 401) {
        try {
          const newToken = await refreshAccessToken();
          // 새로운 엑세스 토큰으로 원래 요청을 다시 시도
          config.headers = {
            ...config.headers,
            Authorization: `Bearer ${newToken.accessToken}`,
          };
          // if ("refreshToken" in config.data) {
          //   config.data.refreshToken = newToken.refreshToken;
          // }
          const retryResponse = await axios(config);
          return retryResponse.data;
        } catch (refreshError) {
          return Promise.reject(refreshError.message);
        }
      } else {
        // 실패 시 에러 메시지를 reject
        if (error.response && error.response.data) {
          return Promise.reject(error.response.data);
        } else {
          return Promise.reject("An error occurred: " + error.message);
        }
      }
    });
}
