import { USER_ROLE } from "./Enum"

const roles = (userRoles) => {
    if (userRoles === 'authenticated')
        return [USER_ROLE.ADMIN, USER_ROLE.DOCTOR, USER_ROLE.NURSE, USER_ROLE.OWNER, USER_ROLE.PATIENT]
    return userRoles
}

export const Permission = {
    '/appointment': roles([USER_ROLE.PATIENT]),
    '/list-appointment': roles([USER_ROLE.PATIENT]),
    '/nurse/confirmed': roles([USER_ROLE.NURSE]),
    '/nurse/present': roles([USER_ROLE.NURSE]),
    '/doctor/medical': roles([USER_ROLE.DOCTOR]),
    '/doctor/medical/prescription/:medicalId/:patientId': roles([USER_ROLE.DOCTOR]),
    '/doctor/medical/history': roles([USER_ROLE.DOCTOR]),
    '/doctor/medical/history/:medicalId': roles([USER_ROLE.DOCTOR])
}