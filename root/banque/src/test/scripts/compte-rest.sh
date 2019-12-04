#!/usr/bin/env bash

URL=http://localhost:8080/compte/

function create-compte {
  curl -s -X POST -H "Content-Type: application/json" --data-raw '{"valeur": "10.0"}' ${URL} | jq -e '.id'
}

create-compte
