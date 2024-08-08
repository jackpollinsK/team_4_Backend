INSERT INTO jobRoles (role_Name, location_id, capability_id, band_id, closing_Date, description, responsibilities, job_Spec, open_Positions)
SELECT
    CONCAT(c.name, ' ', b.name, ' Role') AS roleName,
    l.id AS location_id,
    c.id AS capability_id,
    b.id AS band_id,
    '2024-12-31' AS closingDate,
    CONCAT('Job description for ', c.name, ' in ', l.name, ' at ', b.name, ' level.') AS description,
    CONCAT('Responsibilities for ', c.name, ' role.') AS responsibilities,
    'https://kainossoftwareltd.sharepoint.com/people/SitePages/Career-Lattice.aspx' AS jobSpec,
    1 AS openPositions
FROM
    location l
    CROSS JOIN capability c
    CROSS JOIN band b;