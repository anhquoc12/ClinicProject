import axios from "axios"
import cookie from 'react-cookies'

const SERVER_CONTEXT = '/ClinicWeb'
const SERVER = 'http://localhost:8080'

export const endpoints = {
    'login': `${SERVER_CONTEXT}/api/login/`,
    'authentication': {
        'current-user': `${SERVER_CONTEXT}/api/current-user/`
    },
    'update-user': `${SERVER_CONTEXT}/api/update/user/`,
    'change-password': `${SERVER_CONTEXT}/api/update/user/password/`,
    'user': {
        'add-patient': `${SERVER_CONTEXT}/api/add/user/patient/`
    }
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
    baseURL: SERVER,
    headers: {
        'Content-Type': contentType['form']
    }
})