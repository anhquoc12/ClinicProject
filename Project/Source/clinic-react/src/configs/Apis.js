import axios from "axios"
import cookie from 'react-cookies'

const SERVER_CONTEXT = '/ClinicWeb'
const SERVER = 'http://localhost:8080'

export const endpoints = {
    'login': `${SERVER_CONTEXT}/api/login/`,
    'authentication': {
        'current-user': `${SERVER_CONTEXT}/api/current-user/`
    },
    'update-user': `${SERVER_CONTEXT}/api/update/user/`
}

export const contentType = {
    'json': 'application/json',
    'form': 'multipart/form-data'
}

export const authUser = (type) => {
    return axios.create({
        baseURL: SERVER,
        headers: {
            'Authorization': cookie.load('token'),
            'Content-Type': contentType[type]
        }
    })
}

export default axios.create({
    baseURL: SERVER
})