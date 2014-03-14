import MySQLdb
import MySQLdb.cursors
import traceback

class Utils:
  @staticmethod
  def database():
    return MySQLdb.connect( host="localhost", user="sql_user", passwd="sql_user_password", db="Baseball", cursorclass=MySQLdb.cursors.DictCursor )

  @staticmethod
  def query(q, p):
    #print q % p
    db = Utils.database()
    cur = db.cursor()
    cur.execute(q, p)
    result = cur.fetchall()
    cur.close()
    db.close()
    return result

  @staticmethod
  def execute(q, p):
    #print q % p
    try:
      db = Utils.database()
      cur = db.cursor()
      cur.execute(q, p)
      count = cur.rowcount
      cur.close()
      db.commit()
      db.close()
      return count
    except Exception:
      traceback.print_exc()
      return -1
      
  @staticmethod
  def execute_id(q, p):
    #print q % p
    db = Utils.database()
    cur = db.cursor()
    cur.execute(q, p)
    cur.close()
    db.commit()
    result = cur.lastrowid
    db.close()
    return result

  @staticmethod
  def status(code, msg):
    return {
      "code" : code,
      "msg"  : msg
    }

  @staticmethod
  def status_more( code, msg ):
    return {
      "status" : {
        "code" : code,
        "msg"  : msg
      }
    }

