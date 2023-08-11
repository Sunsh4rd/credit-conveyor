# credit-conveyor

There are examples of using credit conveyor microservice API

## Creating loan offers

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

## Calculating credit data

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