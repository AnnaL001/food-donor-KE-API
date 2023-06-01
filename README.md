# FoodDonorKE API
An API containing a list of Non-Governmental organizations/charities that collect food for the needy. It is to be consumed the FoodDonorKE mobile application.

## Description
**Endpoints**
### GET & DELETE
| **URL**                              | **HTTP VERB**       | **DESCRIPTION**                       |
|:-------------------------------------|:--------------------|:--------------------------------------|
| /charities/locations                 | GET                 | Retrieve charities by location        |    
| /charities/types                     | GET                 | Retrieve charities by type            |
| /charities/:id                       | GET                 | Retrieve a charity by its id          |
| /requests/locations                  | GET                 | Retrieve donation requests by location|
| charities/:id/requests               | GET                 | Retrieve donation requests by charity |

