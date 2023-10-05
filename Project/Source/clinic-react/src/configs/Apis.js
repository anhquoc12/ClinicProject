import axios from "axios"
import cookie from 'react-cookies'

export const SERVER_CONTEXT = '/ClinicWeb'
export const SERVER = 'http://localhost:8080'

export const endpoints = {
    'login': `${SERVER_CONTEXT}/api/login/`,
    'user': {
        'user-id': (userId) => `${SERVER_CONTEXT}/api/user/${userId}/`,
        'list-patient': `${SERVER_CONTEXT}/api/user/patients/`,
        'list-by-role': (role) => `${SERVER_CONTEXT}/api/user/list/${role}/`,
        'add-doctor': `${SERVER_CONTEXT}/api/admin/user/doctor/add/`,
        'delete': (id) => `${SERVER_CONTEXT}/api/admin/user/delete/${id}/`,
        'add-nurse': `${SERVER_CONTEXT}/api/admin/user/nurse/add/`
    },
    'authentication': {
        'current-user': `${SERVER_CONTEXT}/api/current-user/`,
        'add-patient': `${SERVER_CONTEXT}/api/add/user/patient/`
    },
    'update-user': `${SERVER_CONTEXT}/api/update/user/`,
    'change-password': `${SERVER_CONTEXT}/api/update/user/password/`,
    'specialization': {
        'lists': `${SERVER_CONTEXT}/api/specialization/lists/`,
        'add': `${SERVER_CONTEXT}/api/admin/special/add/`,
        'delete': (id) => `${SERVER_CONTEXT}/api/admin/special/delete/${id}/`
    },
    'appointment': {
        'add': `${SERVER_CONTEXT}/api/appointment/register/`,
        'list': `${SERVER_CONTEXT}/api/appointment/lists/`,
        'cancle': `${SERVER_CONTEXT}/api/appointment/cancle/`,
        'confirmed': `${SERVER_CONTEXT}/api/nurse/list-appoitment/confirm/`,
        'confirm-appointment': `${SERVER_CONTEXT}/api/nurse/appointment/confirm/`,
        'present': `${SERVER_CONTEXT}/api/nurse/list-appoitment/present/`,
        'present-appointment': `${SERVER_CONTEXT}/api/nurse/appointment/present/`,
        'patient-medicals': `${SERVER_CONTEXT}/api/doctor/list-patients/`
    },
    'medical': {
        'add': `${SERVER_CONTEXT}/api/doctor/medical/add/`,
        'add-presrciption': (id) => `${SERVER_CONTEXT}/api/doctor/medical/prescription/${id}/`,
        'history': `${SERVER_CONTEXT}/api/doctor/history/`,
        'detail': (id) => `${SERVER_CONTEXT}/api/doctor/history/${id}/`,
        'prescription': (id) => `${SERVER_CONTEXT}/api/doctor/prescription/${id}/`
    },
    'medicine': {
        'list': `${SERVER_CONTEXT}/api/employee/medicine/list/`,
        'delete': (id) => `${SERVER_CONTEXT}/api/admin/medicine/delete/${id}/`,
        'add': `${SERVER_CONTEXT}/api/admin/medicine/add/`,
        'medicine-id': (id) => `${SERVER_CONTEXT}/api/admin/medicine/${id}/`
    },
    'category': {
        'categories': `${SERVER_CONTEXT}/api/employee/medicine/categories/`,
        'delete': (id) => `${SERVER_CONTEXT}/api/admin/medicine/categories/delete/${id}/`,
        'add': `${SERVER_CONTEXT}/api/admin/medicine/categories/add/`
    },
    'unit-medicine': {
        'lists': `${SERVER_CONTEXT}/api/admin/medicine/unit-medicines/`,
        'delete': (id) => `${SERVER_CONTEXT}/api/admin/medicine/unit/delete/${id}/`,
        'add': `${SERVER_CONTEXT}/api/admin/medicine/unit/add/`
    },
    'room': {
        'add': `${SERVER_CONTEXT}/api/admin/room/add/`,
        'list': `${SERVER_CONTEXT}/api/admin/rooms/`,
        'delete': (id) => `${SERVER_CONTEXT}/api/admin/room/delete/${id}/`
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