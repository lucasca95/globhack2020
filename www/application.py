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
    neighborhood = db.Column(db.String(100))
    birthdate = db.Column(db.DateTime)
    email = db.Column(db.String(30))
    password = db.Column(db.String(100))
    _type = db.Column(db.String(1))
    rating = db.Column(db.Float)
    # Relation with Petition
    petitions = db.relationship('Petition', backref='helped')
    # Relation with ReviewHelped
    reviews = db.relationship('ReviewHelped', backref='user')

    def __init__(self, first_name=None, last_name=None, neighborhood=None, birthdate=None, email=None, password=None, _type=None, rating=None):
        self.first_name = first_name
        self.last_name = last_name
        self.neighborhood = neighborhood
        self.birthdate = birthdate
        self.email = email
        self.password = password
        self._type = _type
        self.rating = rating

    def serialize(self):
       return {
           'id'  : self.id,
           'first_name': self.first_name,
           'last_name': self.last_name,
           'neighborhood': self.neighborhood,
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

class UserCollaborator(db.Model):
    __tablename__ = 'collaborator'
    id = db.Column(db.Integer, primary_key=True)
    first_name = db.Column(db.String(20))
    last_name = db.Column(db.String(20))
    neighborhood = db.Column(db.String(100))
    birthdate = db.Column(db.DateTime)
    email = db.Column(db.String(30))
    password = db.Column(db.String(100))
    _type = db.Column(db.String(1))
    rating = db.Column(db.Float)
    # Relation with Petition
    petitions = db.relationship('Petition', backref='collaborator')
    # Relation with ReviewCollaborator
    reviews = db.relationship('ReviewCollaborator', backref='user')

    def __init__(self, first_name=None, last_name=None, neighborhood=None, birthdate=None, email=None, password=None, _type=None, rating=None):
        self.first_name = first_name
        self.last_name = last_name
        self.neighborhood = neighborhood
        self.birthdate = birthdate
        self.email = email
        self.password = password
        self._type = _type
        self.rating = rating

    def serialize(self):
        return {
           'id'  : self.id,
           'first_name': self.first_name,
           'last_name': self.last_name,
           'neighborhood': self.neighborhood,
           'birthdate': self.birthdate,
           'email': self.email,
           'type': self._type,
           'rating': self.rating
        }

class ReviewCollaborator(db.Model):
    __tablename__ = 'reviewcollaborator'
    id = db.Column(db.Integer, primary_key=True)
    comment = db.Column(db.String(140))
    rating = db.Column(db.Float)
    # Relation with UserCollaborator
    user_id = db.Column(db.Integer, db.ForeignKey('collaborator.id'))
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

class Petition(db.Model):
    __tablename__ = 'petition'
    id = db.Column(db.Integer, primary_key=True)
    day = db.Column(db.DateTime)
    hour = db.Column(db.DateTime)
    status = db.Column(db.String(20))
    gift = db.Column(db.String(100))

    # Relation with UserHelped
    helped_id = db.Column(db.Integer, db.ForeignKey('helped.id'))
    # Relation with UserCollaborator
    collaborator_id = db.Column(db.Integer, db.ForeignKey('collaborator.id'))
    
    # Relation with ReviewHelped
    review_helped = db.relationship('ReviewHelped', backref='petition')
    # Relation with ReviewCollaborator
    review_collaborator = db.relationship('ReviewCollaborator', backref='petition')


    def __init__(self, day=None, hour=None, status=None, gift=None):
        self.day = day
        self.hour = hour
        self.status = status
        self.gift = gift

    def serialize(self):
        return {
           'id'  : self.id,
           'day': self.day,
           'hour': self.hour,
           'status': self.status
           'gift': self.gift
        }

######################################
###### DB Access
def findUserHelpedById(user_id):
    u = UserHelped.query.get(user_id)
    print(f'\n\nUser:\n{u}\n')
    if (u):
        return u
    else:
        abort(404, error=f'UserHelped with id {user_id} not found')

def save(e):
    db.session.add(e)
    db.session.commit()
    return e

def delete(e):
    db.session.delete(e)
    db.session.commit()

if __name__ == '__main__':
    app.run(host='0.0.0.0')
