#!/usr/bin/env bash

URL=http://localhost:8080/compte/

function create-compte() {
  curl -s -X POST -H "Content-Type: application/json" --data-raw '{"valeur": "10.0"}' ${URL} | jq -e '.id'
}

function create-autorisation() {
  compte=$1
  shift
  echo ${compte}
  curl -s -X POST -H "Content-Type: application/json" --data-raw '{"montant": "5.0"}' ${URL}${compte}
}

cpt=$(create-compte)
echo "Compte ${cpt}"
create-autorisation ${cpt}
