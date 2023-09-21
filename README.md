# credit-conveyor

## Conveyor microservice API

### Creating loan offers

```http
POST /conveyor/offers
```

**Request body:**

```json
{
  "amount": 300000,
  "term": 18,
  "firstName": "Moses",
  "lastName": "Jackson",
  "middleName": "Fitzgerald",
  "email": "your@mail.com",
  "birthdate": "2004-08-04",
  "passportSeries": "6020",
  "passportNumber": "425513"
}
```

**Response body:**

```json
[
  {
    "applicationId": 1,
    "requestedAmount": 300000,
    "totalAmount": 448000,
    "term": 18,
    "monthlyPayment": 18000,
    "rate": 11,
    "isInsuranceEnabled": true,
    "isSalaryClient": true
  }
]
```

### Calculating credit data

```http
POST /conveyor/calculation
```

**Request body:**

```json
{
  "amount": 300000,
  "term": 18,
  "firstName": "Moses",
  "lastName": "Jackson",
  "middleName": "Fitzgerald",
  "gender": "MALE",
  "birthDate": "2000-08-04",
  "passportSeries": "6020",
  "passportNumber": "425513",
  "passportIssueDate": "2020-09-12",
  "passportIssueBranch": "Issue branch number 5",
  "maritalStatus": "SINGLE",
  "dependentAmount": 3,
  "employment": {
    "employmentStatus": "SELF_EMPLOYED",
    "employerINN": "012345678987",
    "salary": 50000,
    "position": "TOP_MANAGER",
    "workExperienceTotal": 12,
    "workExperienceCurrent": 5
  },
  "account": "98765432100123456789",
  "isInsuranceEnabled": true,
  "isSalaryClient": true
}
```

**Response body:**

```json
{
  "amount": 300000,
  "term": 18,
  "monthlyPayment": 18047.55,
  "rate": 12.5,
  "psk": 448000,
  "isInsuranceEnabled": true,
  "isSalaryClient": true,
  "paymentSchedule": [
    {
      "number": 1,
      "date": "2023-10-05",
      "totalPayment": 30000,
      "interestPayment": 5000,
      "debtPayment": 25000,
      "remainingDebt": 270000
    }
  ]
}
```

## Deal microservice API

### Application for loan offers

```http
POST /deal/application
```

**Request body:**

```json
{
  "amount": 300000,
  "term": 18,
  "firstName": "Moses",
  "lastName": "Jackson",
  "middleName": "Fitzgerald",
  "email": "your@mail.com",
  "birthdate": "2004-08-04",
  "passportSeries": "6020",
  "passportNumber": "425513"
}
```

**Response body:**

```json
[
  {
    "applicationId": 1,
    "requestedAmount": 300000,
    "totalAmount": 448000,
    "term": 18,
    "monthlyPayment": 18000,
    "rate": 11,
    "isInsuranceEnabled": true,
    "isSalaryClient": true
  }
]
```

### Apply provided loan offer and save it to the database

```http
PUT /deal/offer
```

**Request body:**

```json
{
  "applicationId": 11,
  "requestedAmount": 300000,
  "totalAmount": 439070.76,
  "term": 18,
  "monthlyPayment": 24392.82,
  "rate": 12,
  "isInsuranceEnabled": true,
  "isSalaryClient": false
}
```

**Response**

```http
HTTP status
```

### Calculate credit data

```http
PUT /deal/calculate/{applicationId}
```

**Request body:**

```json
{
  "gender": "MALE",
  "maritalStatus": "SINGLE",
  "dependentAmount": 3,
  "passportIssueDate": "2020-09-12",
  "passportIssueBranch": "Issue branch number 5",
  "employment": {
    "employmentStatus": "SELF_EMPLOYED",
    "employerINN": "012345678987",
    "salary": 50000,
    "position": "TOP_MANAGER",
    "workExperienceTotal": 12,
    "workExperienceCurrent": 5
  },
  "account": "98765432100123456789"
}
```

**Response**

```http
HTTP status
```

### Get a loan offer by id

```http
GET /deal/admin/application/{applicationId}
```

**Path parameter**

```http
applicationId
```

**Response**

```json

```

### Get all loan offers

```http
GET /deal/admin/application
```

**Response**

```json

```

## API Gateway microservice

### Applying for loan

```http
POST /application
```

**Request body:**

```json
{
  "amount": 300000, 
  "term": 18, 
  "firstName": "Moses", 
  "lastName": "Jackson", 
  "middleName": "Fitzgerald",
  "email": "your@mail.com", 
  "birthdate": "2004-08-04", 
  "passportSeries": "6020", 
  "passportNumber": "425513"
}
```

**Response body:**

```json
[
  {
    "applicationId": 1,
    "requestedAmount": 300000,
    "totalAmount": 448000,
    "term": 18,
    "monthlyPayment": 18000,
    "rate": 11,
    "isInsuranceEnabled": true,
    "isSalaryClient": true
  }
]
```

### Applying certain loan offer

```http
PUT /deal/offer
```

**Request body:**

```json
{
  "applicationId": 11,
  "requestedAmount": 300000,
  "totalAmount": 439070.76,
  "term": 18,
  "monthlyPayment": 24392.82,
  "rate": 12,
  "isInsuranceEnabled": true,
  "isSalaryClient": false
}
```

**Response**

```http
HTTP status
```

### Calculate credit data

```http
PUT /deal/calculate/{applicationId}
```

**Request body:**

```json
{
  "gender": "MALE",
  "maritalStatus": "SINGLE",
  "dependentAmount": 3,
  "passportIssueDate": "2020-09-12",
  "passportIssueBranch": "Issue branch number 5",
  "employment": {
    "employmentStatus": "SELF_EMPLOYED",
    "employerINN": "012345678987",
    "salary": 50000,
    "position": "TOP_MANAGER",
    "workExperienceTotal": 12,
    "workExperienceCurrent": 5
  },
  "account": "98765432100123456789"
}
```

**Response**

```http
HTTP status
```

### Send an email with creating documents request

```http
POST /deal/document/{applicationId}/send
```

**Path parameter**

```http
applicationId
```

**Response**

```http
HTTP status
```

### Send an email with signing documents request

```http
POST /deal/document/{applicationId}/sign
```

**Path parameter**

```http
applicationId
```

**Response**

```http
HTTP status
```

### Signing documents with received ses code

```http
POST /deal/document/{applicationId}/code
```

**Path parameter**

```http
applicationId
```

**Request body:**

```json
{
  "sesCode" : 1234
}
```

**Response**

```http
HTTP status
```
