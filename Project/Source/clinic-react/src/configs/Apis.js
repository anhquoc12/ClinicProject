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
    },
    'specialization': {
        'lists': `${SERVER_CONTEXT}/api/specialization/lists/`
    },
    'appointment': {
        'add': `${SERVER_CONTEXT}/api/appointment/register/`,
        'list': `${SERVER_CONTEXT}/api/appointment/lists/`,
        'cancle': `${SERVER_CONTEXT}/api/appointment/cancle/`,
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

export const api = (type) => { return axios.create({
    baseURL: SERVER,
    headers: {
        'Content-Type': type
    }
})
}