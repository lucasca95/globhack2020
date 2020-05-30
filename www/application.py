import os
import sys
from datetime import datetime, date, time
from flask import Flask
from flask_sqlalchemy import SQLAlchemy 
from flask_restful import Api, reqparse, abort, Resource, fields, marshal_with


app = Flask(__name__)
app.secret_key = "SuperSecretKey"
app.config['SQLALCHEMY_DATABASE_URI'] = 'postgresql+psycopg2://lucasca95:admin@localhost:5432/db'

db = SQLAlchemy(app)

######################################
###### Modelos
class UserHelped(db.Model):
    __tablename__ = 'helped'
    id = db.Column(db.Integer, primary_key=True)
    first_name = db.Column(db.String(20))
    last_name = db.Column(db.String(20))
    birthdate = db.Column(db.DateTime)
    email = db.Column(db.String(30))
    password = db.Column(db.String(100))
    _type = db.Column(db.String(1))
    rating = db.Column(db.Float)
    # Relation with Petition
    petitions = db.relationship('Petition', backref='helped')
    # Relation with ReviewHelped
    reviews = db.relationship('ReviewHelped', backref='user')

    def __init__(self, first_name=None, last_name=None, birthdate=None, email=None, password=None, _type=None, rating=None):
        self.first_name = first_name
        self.last_name = last_name
        self.birthdate = birthdate
        self.email = email
        self.password = password
        self._type = _type
        self.rating = rating

    def serialize(self):
       """Return object data in easily serializeable format"""
       return {
           'id'  : self.id,
           'first_name': self.first_name,
           'last_name': self.last_name,
           'birthdate': self.birthdate,
           'email': self.email,
           'type': self._type,
           'rating': self.rating
        }

class ReviewHelped(db.Model):
    __tablename__ = 'reviewhelped'
    id = db.Column(db.Integer, primary_key=True)
    comment = db.Column(db.String(140))
    rating = db.Column(db.Float)
    # Relation with User
    user_id = db.Column(db.Integer, db.ForeignKey('helped.id'))
    # Relation with Petition
    petition_id = db.Column(db.Integer, db.ForeignKey('petition.id'))

    def __init__(self, comment=None, rating=None, gift=None):
        self.comment = comment
        self.rating = rating

    def serialize(self):
       """Return object data in easily serializeable format"""
       return {
           'id'  : self.id,
           'comment': self.comment,
           'rating': self.rating
        }


if __name__ == '__main__':
    app.run(host='0.0.0.0')
