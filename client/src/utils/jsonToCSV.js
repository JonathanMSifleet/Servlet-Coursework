const csvToJSON = (formData) => {
  return Object.values(formData)
    .map((attribute) => attribute.toString())
    .join(',,');
};

export default csvToJSON;
